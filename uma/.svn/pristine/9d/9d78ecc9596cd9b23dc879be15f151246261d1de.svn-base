package uma

class AllMain {
	static init() {
		// 設定ファイル読み込み
		Prop.load();
		
		// プロキシの情報を設定
		if (Prop.PROXY) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Prop.PROXY_ADDRESS, Prop.PROXY_PORT));
			HttpUtil.setProxy(proxy);
		}
	}

	/** main */
	static main(args){
		init();
       
		// 騎手、調教師情報の読み込み
		Map<String, List> manData = Excel2Data.excel2Data();

		// cliborツールからデータ読み込み
//		List raceInfo = CliborInput.parseFile();
	
		// 	馬柱データ読み込み(Web版)
		JraWeb.getUmaBashiraData();

		// 	結果データ読み込み(Web版)
//		JraWeb.getResultData();
		
		//  馬柱データの読み込み
		 List raceInfo = JraWeb.parseFileUB();
		
		//  結果データの読み込み
//		 List raceInfo = JraWeb.parseFileRS();
        
		// 対象馬抽出処理
		Misyori.misyoriList(manData, raceInfo);
		
		//TODO: 分析処理

		log "end.";
	}
    static void log(Object obj){if(Prop.DEBUG) {System.err.println obj;}}
}
