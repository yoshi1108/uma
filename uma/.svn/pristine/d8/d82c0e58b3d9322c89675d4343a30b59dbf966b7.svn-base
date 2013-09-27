package uma;
class UmaInfo{
    /** 枠番 */
    int waku;
    /** 馬番 */
    int umaban;
    /** 馬名 */
    String uma;
    /** 騎手名 */
    String kishu;
    /** 調教師名 */
    String tyokyo;
    /** 人気 */
    int ninki;
    /** 着順 */
    int jun;
    /** オッズ */
    double ozz;
    /** 設定完了フラグ */
    boolean complete = false;
    /** 騎手複勝率 */
    int kishuFuku;
    /** 調教師複勝率 */
    int tyoFuku;
    /** 軸フラグ */
    boolean jiku = false;
    /** 抽出優先度 */
    int select = 0;

    public String toString() {
        String str = String.format("%02d枠, %02d番, %02d着(%2d人気), %5s倍, %-15s, %-6s, %-6s",
                                    waku, umaban, jun, ninki, ozz, uma, kishu, tyokyo);
        return str;
    }
}