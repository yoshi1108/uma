package uma

class Prop {
	static Properties prop = new Properties();

    /** 設定変数 */
    static int PROXY_PORT;
    static String PROXY_ADDRESS;
    static boolean PROXY;
    static String JRA_URL;
	static int KISHU_TOP_NUM;
	static int TYO_TOP_NUM;
    static boolean DEBUG;
	static String BASE_DIR;
	static int SELECT_FUKURATE;
    
    /** 内部生成変数 */
	static String CLIBOR_DIR;
	/** JRAのWebから取得した馬柱ファイル保存場所 */
	static String OUTPUT_UB;
	/** JRAのWebから取得した結果ファイル保存場所 */
	static String OUTPUT_RS;
	static String MAN_DIR;

	static save(){
        prop.get("PROXY_PORT", PROXY_PORT)
        prop.get("PROXY_ADDRESS", PROXY_ADDRESS)
        prop.get("PROXY", PROXY)
        prop.get("JRA_URL", JRA_URL)
        prop.get("KISHU_TOP_NUM", KISHU_TOP_NUM)
        prop.get("TYO_TOP_NUM",TYO_TOP_NUM)
        prop.get("DEBUG", DEBUG)
        prop.get("BASE_DIR", BASE_DIR)
        prop.get("SELECT_FUKURATE", SELECT_FUKURATE)
		new File(Const.PROP_FILE).withOutputStream{ outStream ->
			prop.store(outStream, 'property');
		}
	}

	static load(){
		new File(Const.PROP_FILE).withInputStream{ inStream ->
			prop.load(inStream)
		}
        // 設定変数読み込み 
        PROXY_PORT = prop.get("PROXY_PORT").toInteger();
		PROXY_ADDRESS = prop.get("PROXY_ADDRESS");
        PROXY = prop.get("PROXY").toBoolean();
        JRA_URL = prop.get("JRA_URL");
		KISHU_TOP_NUM = prop.get("KISHU_TOP_NUM").toInteger();
		TYO_TOP_NUM = prop.get("TYO_TOP_NUM").toInteger();
        DEBUG = prop.get("DEBUG").toBoolean();
		BASE_DIR = prop.get("BASE_DIR");
		SELECT_FUKURATE = prop.get("SELECT_FUKURATE").toInteger();
       
        // 内部変数設定 
		CLIBOR_DIR = BASE_DIR + "/clibor/ch";
		OUTPUT_UB = BASE_DIR + "/outputUB";
		OUTPUT_RS = BASE_DIR + "/outputRS";
		MAN_DIR = BASE_DIR + "/manual1";
	}
}
