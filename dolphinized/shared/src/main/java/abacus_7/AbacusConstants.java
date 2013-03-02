package abacus_7;

public class AbacusConstants {

    public static final int    ROW_COUNT    = 10;
    public static final int    COL_COUNT    = 10;

    public static final String TYPE_BALL    = unique("BALL");
    public static final String TYPE_RAIL    = unique("RAIL");

    public static final String PM_TOUCHED   = unique("touched");

    public static final String ATT_SCALE    = "scale";

    public static final String ATT_RAIL_ID  = "rail-id";
    public static final String ATT_DIGIT    = "digit";
    public static final String ATT_ON       = "on";

    public static final String CMD_CREATE_BALLS = unique("createBalls");
    public static final String CMD_TOGGLE       = unique("toggle");

    public static final String POSITION_PREFIX  = "BALL-RAIL-";

    private static String unique(String key) {
        return AbacusConstants.class.getName() + "." + key;
    }

    public static String ballId(int scale, int digit) {
        return POSITION_PREFIX + scale + "-" + digit;
    }
}
