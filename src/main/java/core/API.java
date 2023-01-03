package core;

public class API {

    public static final String HOST = "http://192.168.1.171:8079/";

    public static final String LEVEL_PICTURE = HOST + "level-picture";
    public static final String TILES_PICTURE_70X70 = HOST + "tile-picture-70X70";
    public static final String LEVEL_MAP = HOST + "level-map";
    public static final String SIGN_IN = HOST + "web-sign-in";
    public static final String PLAYER_BASIC_DATA = HOST + "web-get-player-basic-data";
    public static final String API_GET_LEVELS = HOST + "web-get-levels-statistics";
    public static final String API_GET_TILES = HOST + "web-get-tiles";

    public static final String API_SIGN_OUT = HOST + "/sign-out";
    public static final String API_GET_SETTINGS = HOST + "/get-settings";
    public static final String API_GET_INVENTORY = HOST + "/get-units";
}
