package core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import core.levelscreen.objects.Level;
import core.screens.GameScreen;

import java.util.ArrayList;

public class Constants {
    public static final float PPM = 32.0f;
    public static final int SCREENWIDTH = 1200;
    public static final int SCREENHEIGHT = 800;

    public static final  String LOGINBACKGROUND = "backgrounds/login-background.png";
    public static final String SKIN = "skins/skin/gappy-skin.json";
    public static final String PLAYER_NORMAL = "player/player_normal/player_normal.png";
    public static final String PLAYER_LEFT = "player/player_left/player_left.png";
    public static final String PLAYER_LEFT_SWORD = "player/player_left/player_left_sword.png";
    public static Sprite PLAYER_LEFT_SPRITE;
    public static final String PLAYER_RIGHT = "player/player_right/player_right.png";
    public static final String PLAYER_RIGHT_SWORD = "player/player_right/player_right_sword.png";
    public static Sprite PLAYER_RIGHT_SPRITE;
    public static final String BULLET = "gameScreen/bullet/bullet.png";
    public static final String ENEMY_NORMAL = "enemy/enemy_normal/enemy_normal.png";
    public static final String ENEMY_LEFT = "enemy/enemy_left/enemy_left.png";
    public static final String ENEMY_RIGHT = "enemy/enemy_right/enemy_right.png";
    public static final String HOSTAGE_NORMAL = "hostage/hostage_normal/hostage_normal.png";
    public static final String HOSTAGE_LEFT = "hostage/hostage_left/hostage_left.png";
    public static final String HOSTAGE_RIGHT = "hostage/hostage_right/hostage_right.png";
    public static Boot INSTANCE;
    public static OrthographicCamera ORTHOGRAPHIC_CAMERA;
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;

    // Database constants
    public static String USERNAME;
    public static String RANK;
    public static String GAME_TOKEN;

    // GameScreen
    public static GameScreen GAME_SCREEN;
    public static int ENEMY_KILLS = 0;
    public static boolean RESET_CAMERA_POSITION;
    public static int KEY_LEFT;
    public static int KEY_RIGHT;
    public static int KEY_JUMP;
    public static int KEY_SHOOT;

    // LevelsScreen
    public static ArrayList<Level> LEVEL_LIST;
    public static final int SCROLL_PANE_SIZE = 800;

}