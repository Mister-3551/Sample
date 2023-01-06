package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.screens.levelsscreen.Level;

import java.util.ArrayList;

public class GameData {

    public static Boot INSTANCE;
    public static int CURRENT_LEVEL;
    public static ArrayList<Level> LEVEL_LIST;
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;
    public static final float PPM = 32.0f;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static class Player {
        public static Long PLAYER_ID;
        public static String PLAYER_USERNAME;
        public static int PLAYER_RANK;
        public static String PLAYER_GAME_TOKEN;
        public static int PLAYER_KEY_LEFT;
        public static int PLAYER_KEY_RIGHT;
        public static int PLAYER_KEY_JUMP;
        public static int PLAYER_KEY_SHOOT;

        public static class Sprite {
            public static com.badlogic.gdx.graphics.g2d.Sprite PLAYER_LEFT_SPRITE;
            public static com.badlogic.gdx.graphics.g2d.Sprite PLAYER_RIGHT_SPRITE;
        }
    }

    public static class GameScreen {
        public static core.screens.GameScreen GAME_SCREEN;
        public static int ENEMY_KILLS = 0;

        public static class Camera {
            public static OrthographicCamera ORTHOGRAPHIC_CAMERA;
            public static boolean RESET_CAMERA_POSITION;
        }
    }

    public static class LevelScreen {
        public static ArrayList<Level> LEVEL_LIST;
        public static final float SCROLL_PANE_SIZE = 1200.0f;
    }

    public static class Directory {

        public static String LEVEL_PICTURE_DIRECTORY;
        public static String WELCOME_PICTURE_DIRECTORY;
        public static String LEVEL_MAP_DIRECTORY;
        public static String TILED_DIRECTORY_70X70;
        public static String SKINS_DIRECTORY;

        public static final String TEMPORARY_MAP_NAME = "temporary-map.tmx";
    }

    public static class Skins {

        public static final String SKIN = "skins/skin/gappy-skin.json";

        public static class Player {
            public static String PLAYER_NORMAL;
            public static String PLAYER_LEFT;
            public static String PLAYER_RIGHT;
            public static final String PLAYER_LEFT_SWORD = "player/player_left/player_left_sword.png";
            public static final String PLAYER_RIGHT_SWORD = "player/player_right/player_right_sword.png";
        }

        public static class Enemy {
            public static String ENEMY_NORMAL;
            public static String ENEMY_LEFT;
            public static String ENEMY_RIGHT;
        }

        public static class Hostage {
            public static final String HOSTAGE_NORMAL = "hostage/hostage_normal/hostage_normal.png";
            public static final String HOSTAGE_LEFT = "hostage/hostage_left/hostage_left.png";
            public static final String HOSTAGE_RIGHT = "hostage/hostage_right/hostage_right.png";
        }

        public static class Bullet {
            public static final String BULLET = "gameScreen/bullet/bullet.png";
        }

        public static class Cursor {
            public static final String AIM_CURSOR = "gameScreen/aim/aim.png";
            public static final String SWORD_CURSOR = "sword/sword.png";
        }
    }
}