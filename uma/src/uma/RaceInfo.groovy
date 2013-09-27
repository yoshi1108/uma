package uma;
class RaceInfo{
    /** レース情報 */
    String raceStatus;

    /** 馬情報リスト */
    List<UmaInfo> umaInfoList;

    String fileName;
    /** 日付 */
    String date;
    /** 開催場 */
    String place;
    /** レース番 */
    String R;
    /** 条件(芝、ダート) */
    String cond;
    /** 距離 */
    String len;

    public void parseStatus(String raceStr){
        String[] tmp = raceStr.replaceAll(/ +/," ").split(/[ \t]/);

        // 年月日部分の処理
        String[] dateArr = tmp[0].split(/[年月日]/);
        int dateY = dateArr[0].toInteger();
        int dateM = dateArr[1].toInteger();
        int dateD = dateArr[2].toInteger();
        date = String.format("%04d_%02d_%02d", dateY, dateM, dateD);

        place = tmp[1].replaceAll(/^[0-9]*回/, "").replaceAll(/[0-9]*日$/,"");

        R = String.format("%02d", tmp[2].replaceAll(/R/,"").toInteger());

        // 距離抽出
        (raceStatus =~ / ([0-9]*)m/).each {m0,m1->
            len=m1;
        }

        // 芝・ダート判定
        if (raceStatus.matches(".*ダート.*")) {
            cond = "ダ";
        }else {
            cond = "芝";
        }

        fileName = "JRA_" + date + "_" + place + "_" + R + "R_" + cond + "_" + len + ".txt";
    }

    public String toString() {
        String result = raceStatus + "\n";
        result += fileName + "\n";

        (umaInfoList).each{umaInfo ->
            result += umaInfo.toString() + "\n";
        }
        return result;
    }
}