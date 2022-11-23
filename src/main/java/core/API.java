package core;

import com.badlogic.gdx.utils.Array;

public class API {

    public static final String HOST = "http://localhost:8079";
    public static final String API_SIGN_IN = HOST + "/sign-in/u/%s/p/%s";
    public static final String API_SIGN_OUT = HOST + "/sign-out/t/%s";
    public static final String API_GET_LEVELS = HOST + "/get-levels/t/%s";
    public static final String API_GET_SETTINGS = HOST + "/get-settings/t/%s";
}
