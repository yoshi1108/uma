package uma

import java.util.List;

class JraWeb {
	/** 馬柱データ読み込み(Web版) */
	static void getUmaBashiraData() {
		// トップページ
		String pageJra = HttpUtil.httpGet(Prop.JRA_URL);
		// 出馬表ページ
		def doaSyutuba = ((List)JraWebUtil.getDoAction(pageJra, ".*>出馬表<.*")).get(0);
		def pageSyutuba = JraWebUtil.getPage(Prop.JRA_URL, doaSyutuba);

		// 開催場、日にち毎
		def doaJoBtnList = JraWebUtil.getDoAction(pageSyutuba, ".*joBtn.*");
		for(JraDoAction doAction:doaJoBtnList) {
			def pagePlaceDay = JraWebUtil.getPage(Prop.JRA_URL, doAction);
			def doaRaceList = JraWebUtil.getDoAction(pagePlaceDay, ".*return.*bmd.*");
			for(JraDoAction doaRace:doaRaceList) {
				// 馬柱ページ
				def pageRace = JraWebUtil.getPage(Prop.JRA_URL, doaRace);
				JraWebUtil.createFileUB(Prop.OUTPUT_UB, pageRace);
			}
		}
	}
	
	/** 結果データ読み込み(Web版) */
	static void getResultData() {
		// トップページ
		String pageJra = HttpUtil.httpGet(Prop.JRA_URL);
		// 出馬表ページ
		def doaKekka = ((List)JraWebUtil.getDoAction(pageJra, ".*>レース結果<.*")).get(0);
		def pageKekka = JraWebUtil.getPage(Prop.JRA_URL, doaKekka);

		// 開催場、日にち毎
		def doaJoBtnList = JraWebUtil.getDoAction(pageKekka, ".*srl.*joBtn.*");
		for(JraDoAction doAction:doaJoBtnList) {
			def pagePlaceDay = JraWebUtil.getPage(Prop.JRA_URL, doAction);
			def doaRaceList = JraWebUtil.getDoAction(pagePlaceDay, ".*return.*sde.*");
			for(JraDoAction doaRace:doaRaceList) {
				// 結果ページ
				def pageRace = JraWebUtil.getPage(Prop.JRA_URL, doaRace);
				JraWebUtil.createFileRS(Prop.OUTPUT_RS, pageRace);
			}
		}
	}

    /** JRAの結果入力 */
    static List parseFileRS() {
        List raceInfoList = new ArrayList(); // 入力レース情報
        for (File inputFile : (new File(Prop.OUTPUT_RS)).listFiles()) {
            if(!inputFile.isFile()) {continue };
            log inputFile;
            RaceInfo raceInfo = parceFileRSOne(inputFile.readLines());
            raceInfoList.add(raceInfo);
        }
        return raceInfoList;
    }

    
    /** レース情報１ファイルの処理 */
    static RaceInfo parceFileRSOne(List<String> page) {
        List umaInfoList = new ArrayList();
        int dataLine = -1; // 有効データ開始からの行数
        String raceStatus = ""; // レース条件データ
        String targetLine = ""; // 評価対象のライン文字列(実際の行ではなく、２行をくっつけて評価したりするので)
        boolean umaDataFlag = false; // 馬、騎手情報データ部分かどうか
       
        // レース日付場所
        List subTitleList = TextUtil.grepStr(page, ".*subTitle.*")
        subTitleList = TextUtil.getText(subTitleList)
       
        def subTitle = ""
        subTitleList.each {subTitle += it + " "}
        // R取得
        String R = ((List)TextUtil.grepBand(page, ".*kekkaRaceTitle.*", ".*<.div>.*")).get(0);
        // 条件取得
        def kekkaRaceJoken = ""
        TextUtil.getText(TextUtil.grepBand(page, ".*kekkaRaceJoken.*", ".*<.div>.*")).each{
            kekkaRaceJoken += it + " "
        }
        raceStatus = "${subTitle} ${R} ${kekkaRaceJoken}"
        log raceStatus
        
        def umaListStr = TextUtil.grepBandList(page, "tyakuTd", "</tr>", 100)
        
        umaListStr.each {
            def tmp = it.get(0)
            if(tmp.isInteger()){
                def umaInfo = new UmaInfo();
                umaInfo.jun = it.get(0).toInteger()
                umaInfo.waku = it.get(1).toInteger()
                umaInfo.umaban = it.get(2).toInteger()
                umaInfo.uma = it.get(3)
                umaInfo.ninki = it.get(4).replaceAll(/[^0-9]+/,"").toInteger()
                umaInfo.kishu = it.get(8).replaceAll(/\([^\)]*\)/, "").replaceAll(/[★☆▲▼]/,"")
                umaInfo.tyokyo = it.get(9).replaceAll(/\([^\)]*\)/, "")
                log umaInfo
                umaInfoList.add(umaInfo);
            }
        }
        /** レース情報生成 */
        RaceInfo raceInfo = new RaceInfo();
        raceInfo.setRaceStatus(raceStatus);
        raceInfo.parseStatus(raceStatus);
        raceInfo.setUmaInfoList(umaInfoList);
        return raceInfo;
    }
   
    /** JRAの馬柱入力 */
    static List parseFileUB() {
        List raceInfoList = new ArrayList(); // 入力レース情報
        for (File inputFile : (new File(Prop.OUTPUT_UB)).listFiles()) {
            if(!inputFile.isFile()) {continue };
            log inputFile;
            RaceInfo raceInfo = parceFileUBOne(inputFile.readLines());
            raceInfoList.add(raceInfo);
        }
        return raceInfoList;
    }
    
    /** レース情報１ファイルの処理 */
    static RaceInfo parceFileUBOne(List<String> page) {
        List umaInfoList = new ArrayList();
        int dataLine = -1; // 有効データ開始からの行数
        String raceStatus = ""; // レース条件データ
        String targetLine = ""; // 評価対象のライン文字列(実際の行ではなく、２行をくっつけて評価したりするので)
        boolean umaDataFlag = false; // 馬、騎手情報データ部分かどうか
       
        // レース日付場所
        List subTitleList = TextUtil.grepStr(page, ".*subTitle.*")
        subTitleList = TextUtil.getText(subTitleList)
        def subTitle = ""
        subTitleList.each {subTitle += it + " "}
       
        // R取得
        String R = ((List)TextUtil.grepBand(page, ".*kyosoMei.*", ".*<.div>.*")).get(0);
        // 条件取得
        def kyosoJoken = ""
        TextUtil.getText(TextUtil.grepBand(page, ".*kyosoJoken.*", ".*<.div>.*")).each{
            kyosoJoken += it + " "
        }
        raceStatus = "${subTitle} ${R} ${kyosoJoken}"
        println raceStatus
        
        def umaListStr = TextUtil.grepBandList(page, "wban waku", "</tr>", 100)
        
        umaListStr.each {
            if(it.get(0).isInteger() && it.get(1).isInteger()){
                def umaInfo = new UmaInfo();
                umaInfo.waku = it.get(0).toInteger()
                umaInfo.umaban = it.get(1).toInteger()
                umaInfo.uma = it.get(2)
                umaInfo.ozz = it.get(3).toDouble()
                umaInfo.ninki = it.get(4).replaceAll(/[^0-9]+/,"").toInteger()
                umaInfo.kishu = it.get(9).replaceAll(/\([^\)]*\)/, "").replaceAll(/[△▽★☆▲▼]/,"")
                umaInfo.tyokyo = it.get(10).replaceAll(/\([^\)]*\)/, "")
                umaInfoList.add(umaInfo);
                log umaInfo
            }
        }
        /** レース情報生成 */
        RaceInfo raceInfo = new RaceInfo();
        raceInfo.setRaceStatus(raceStatus);
        raceInfo.parseStatus(raceStatus);
        raceInfo.setUmaInfoList(umaInfoList);
        return raceInfo;
    }
    
    static void log(Object obj){if(Prop.DEBUG) {System.err.println obj;}}
}
