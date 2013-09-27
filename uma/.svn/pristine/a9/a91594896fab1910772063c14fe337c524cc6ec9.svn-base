package uma

import org.codehaus.groovy.ast.stmt.SwitchStatement;

class JraInput {
    static final String dataDir = "data";
    static final String cliborDir = dataDir + "/" + "clibor/ch";
    static final int umaInfoOffset = 10; // 有効データ行数の何行目から馬の情報が始まるか
    static final int umaInfoStep = 4;   // 馬情報が何行ずつか
    static final int raceInfoNum = 4;   // レース情報の行数
    static boolean debugMode=true;
    static Set umaInfoList = new ArrayList();
    static UmaInfo currentUmaInfo = new UmaInfo();
    /** main */
    static main(args){
        parseFile();
    }
    static void debugPrint(Object obj){
        if(debugMode) {
            System.err.println obj;
        }
    }
    /** JRAの馬柱情報入力 */
    static parseFile() {
        // eachだのfindだとbreakもcontinueもできないのでforで実装
        for (File inputFile : (new File(cliborDir)).listFiles()) {
            debugPrint inputFile;
            String[] lineList = inputFile.readLines();
            int dataLine = -1; // 有効データ開始からの行数
            String raceInfo = ""; // レース条件データ
            String targetLine = ""; // 評価対象のライン文字列(実際の行ではなく、２行をくっつけて評価したりするので)
            for (int cnt=0; cnt<lineList.length; cnt++) {
                String line = lineList[cnt];
                debugPrint "${dataLine} ${line}"; // デバッグ文
                if(dataLine == -1){
                    if(line =~/^[0-9][0-9][0-9][0-9]年.*/){
                        dataLine = 0;
                    } else {
                        continue;
                    }
                }
                dataLine++;
                if(0 <= dataLine && dataLine <= raceInfoNum) {
                    raceInfo += line + " "; // レース情報編集
                }
                if (dataLine < umaInfoOffset) {
                    continue;
                }
                if (lineList[cnt+1] ==~ /^.*ブリンカー.*$/) {
                    // 次の行にブリンカーを含む行は前の行とくっつけて評価する
                    targetLine = line + lineList[cnt+1].replaceAll("ブリンカー","");
                    cnt++ ; // 先読みだししたのでファイル読み込み行数のカウンタを1進める
                }else {
                    targetLine = line;
                }
                parseUmaInfo(targetLine, dataLine);
            }
            dumpUmaInfo();
            println raceInfo;
        }
        println "end.";
    }
   
    static void parseUmaInfo(String targetLine, int dataLine) {
        def umaInfo;
        switch ((dataLine-umaInfoOffset) % 4) {
            case 0:
                int tmpIdx=0;
                umaInfo = new UmaInfo();
                String[] tmp = targetLine.split(/[ \t]/);
                umaInfo.waku = tmp[tmpIdx++].toInteger();
                def alsoUmaban = tmp[tmpIdx++];
                if (alsoUmaban.isInteger()) {
                    umaInfo.uma = tmp[tmpIdx++];
                }else {
                    umaInfo.umaban = umaInfo.waku;
                    umaInfo.uma = alsoUmaban;
                }
                umaInfo.ozz = tmp[tmpIdx++].toDouble();
                umaInfo.ninki = ((tmp[tmpIdx++]).replaceAll("[^0-9]", "")).toInteger();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
    
    static void log(Object obj){if(Prop.DEBUG) {System.err.println obj;}}
}