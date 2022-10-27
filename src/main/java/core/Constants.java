package core;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Constants {
    public static final float PPM = 32.0f;
    public static final int SCREENWIDTH = 1200;
    public static final int SCREENHEIGHT = 800;

    public static final  String LOGINBACKGROUND = "backgrounds/login-background.png";
    public static final String SKIN = "skins/skin/gappy-skin.json";
    public static final String PLAYER_NORMAL = "player/player_normal/player_normal.png";
    public static final String PLAYER_LEFT = "player/player_left/player_left.png";
    public static final String PLAYER_LEFT_SWORD = "player/player_left/player_left_sword.png";
    public static final String PLAYER_RIGHT = "player/player_right/player_right.png";
    public static final String PLAYER_RIGHT_SWORD = "player/player_right/player_right_sword.png";
    public static final String ENEMY_NORMAL = "enemy/enemy_normal/enemy_normal.png";
    public static final String ENEMY_LEFT = "enemy/enemy_left/enemy_left.png";
    public static final String ENEMY_RIGHT = "enemy/enemy_right/enemy_right.png";
    public static Boot INSTANCE;
    public static OrthographicCamera orthographicCamera;

    // Database constants
    public static String USERNAME;
    public static String RANK;
    public static String GAMETOKEN;

}