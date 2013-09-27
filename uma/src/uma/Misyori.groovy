package uma

import java.util.List;
import java.util.Map;

class Misyori {
	
	/** 対象馬抽出処理 */
	static void misyoriList(Map<String, List> manDataMap, List raceInfoList){
		for(RaceInfo raceInfo:raceInfoList) {
			misyori(manDataMap, raceInfo);
		}
	}
	
	/** 対象馬抽出処理 */
	static List<UmaInfo> misyori(Map<String, List> manDataMap, RaceInfo raceInfo){
		log raceInfo.getRaceStatus();

		// 抽出馬のリスト
		Map selectMap = new LinkedHashMap();

		// 騎手TOP抽出
		String manKey = "${raceInfo.place}_${raceInfo.cond}_${raceInfo.len}_騎手";
		List kishuTopX = genTop(raceInfo.getUmaInfoList(), manDataMap, manKey, "騎手");
		log kishuTopX;

		// 調教師TOP抽出
		manKey = "${raceInfo.place}_${raceInfo.cond}_${raceInfo.len}_調教師";
		List tyoTopX = genTop(raceInfo.getUmaInfoList(), manDataMap, manKey, "調教師");
		log tyoTopX;

		// 軸馬抽出
		for(String kishu: kishuTopX.take(Prop.KISHU_TOP_NUM)) {
			String kishuName = (kishu.split(","))[0];
			for(UmaInfo umaInfo: raceInfo.umaInfoList) {
				if(kishuName.equals(umaInfo.kishu)) {
					for(String tyokyo: tyoTopX.take(Prop.TYO_TOP_NUM)) {
						String tyoName = (tyokyo.split(","))[0];
						if(tyoName.equals(umaInfo.tyokyo)) {
							umaInfo.setKishuFuku((kishu.split(","))[1].toInteger());
							umaInfo.setTyoFuku((tyokyo.split(","))[1].toInteger());
							umaInfo.setJiku(true);
							umaInfo.setSelect(selectMap.size());
							selectMap.put(umaInfo.umaban, umaInfo);
						}
					}
				}
			}
		}
		// 騎手条件抽出
		for(String nameFuku: kishuTopX.take(Prop.KISHU_TOP_NUM)) {
			String name = (nameFuku.split(","))[0];
			for(UmaInfo umaInfo: raceInfo.umaInfoList) {
				int fuku = (nameFuku.split(","))[1].toInteger();
				if(name.equals(umaInfo.kishu) && fuku >= Prop.SELECT_FUKURATE) {
					if(!selectMap.containsKey(umaInfo.umaban)){
						umaInfo.setKishuFuku(fuku);
						for(String tyokyo: tyoTopX) {
							if(umaInfo.tyokyo.equals((tyokyo.split(","))[0])){
								umaInfo.setTyoFuku(((tyokyo.split(","))[1]).toInteger());
								break;
							}
						}
						umaInfo.setSelect(selectMap.size());
						selectMap.put(umaInfo.umaban, umaInfo);
					}
				}
			}
		}
		// 調教師条件抽出
		for(String nameFuku: tyoTopX.take(Prop.TYO_TOP_NUM)) {
			String name = (nameFuku.split(","))[0];
			for(UmaInfo umaInfo: raceInfo.umaInfoList) {
				int fuku = (nameFuku.split(","))[1].toInteger();
				if(name.equals(umaInfo.tyokyo) && fuku >= Prop.SELECT_FUKURATE) {
					if(!selectMap.containsKey(umaInfo.umaban)){
						umaInfo.setTyoFuku(fuku);
						for(String kishu: kishuTopX) {
							if(umaInfo.kishu.equals((kishu.split(","))[0])){
								umaInfo.setKishuFuku(((kishu.split(","))[1]).toInteger());
								break;
							}
						}
						umaInfo.setSelect(selectMap.size());
						selectMap.put(umaInfo.umaban, umaInfo);
					}
				}
			}
		}
		List resultList = new ArrayList();
		(selectMap.values()).each {
			resultList.add(it);
			log tyuStr(it);
		}
		return resultList;
	}

	/** 騎手、および調教師トップXを抽出 */
	static List genTop(List umaInfoList, Map manDataMap, String manKey, String mode) {
		// 騎手TOP抽出
		Map<Double, String> tmpSort = new TreeMap<Double, String>();
		for(UmaInfo umaInfo: umaInfoList) {
			for(Map manMap: manDataMap.get(manKey)){
				String manData = manMap.get(mode);

				String umaInfoName;
				if("調教師".equals(mode)) {
					umaInfoName = umaInfo.tyokyo;
				} else {
					umaInfoName = umaInfo.kishu;
				}
				if(manData.matches(/.*${umaInfoName}.*/)) {
					String fukuRate = manMap.get("複勝率");
					tmpSort.put(fukuRate.toDouble(), umaInfoName);
					break;
				}
			}
		}
		List topX = new ArrayList();
		for(Double fukuRate: new ArrayList(tmpSort.keySet()).reverse()) {
			String fukuRateStr = (fukuRate * 100).toInteger();
			topX.add(tmpSort.get(fukuRate) + "," + fukuRateStr);
		}
		return topX;
	}

	static String tyuStr(UmaInfo umaInfo){
		String mark = "  ";
		if(umaInfo.jiku){
			switch(umaInfo.select) {
				case 0: mark="◎";break;
				case 1: mark="○";break;
				case 2: mark="▲";break;
			}
		}
		String teki = "　";
		if(1<= umaInfo.jun && umaInfo.jun <= 3) {
			teki="■";
		}

		return String.format("%s%s %02d着 %02d人気 %02d:%-12s,%-10s,%-10s"
			,mark, teki, umaInfo.jun, umaInfo.ninki, umaInfo.umaban, umaInfo.uma
			,umaInfo.kishu + "(" + umaInfo.kishuFuku + ")"
			,umaInfo.tyokyo + "(" + umaInfo.tyoFuku + ")");
	}
	
    static void log(Object obj){if(Prop.DEBUG) {System.err.println obj;}}
}
