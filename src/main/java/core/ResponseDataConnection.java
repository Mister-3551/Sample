package core;

import com.badlogic.gdx.utils.Json;
import core.screens.skinscreen.Skin;
import core.objects.Tile;
import core.screens.levelsscreen.Level;
import core.screens.menuscreen.PlayerBasicData;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class ResponseDataConnection {

    public static class SignInScreen {
        public static boolean userAuthentication(String username, String password) throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("usernameEmail", username)
                    .add("password", password)
                    .build();
            String response = ApiResponse.getResponse(API.SIGN_IN, formBody);
            if (!response.isBlank()) {
                setUserData(response);
                return true;
            }
            return false;
        }

        private static void setUserData(String response) throws Exception {
            var object = ApiResponse.getDataFromGameToken(response);
            GameData.Player.PLAYER_ID = (Long) object.get("id");
        }

    }
    public static class MenuScreen {
        public static void getPlayerBasicData() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("idUser", String.valueOf(GameData.Player.PLAYER_ID))
                    .build();
            String response = ApiResponse.getResponse(API.PLAYER_BASIC_DATA, formBody);

            PlayerBasicData playerBasicData = new Json().fromJson(PlayerBasicData.class, PlayerBasicData.class, response);

            GameData.Player.PLAYER_USERNAME = playerBasicData.getUsername();
            GameData.Player.PLAYER_RANK = playerBasicData.getRank();
            GameData.CURRENT_LEVEL = playerBasicData.getCurrentLevel();
        }
    }

    public static class LevelsScreen {
        public static ArrayList getLevels() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("idUserOrUsername", String.valueOf(GameData.Player.PLAYER_ID))
                    .build();
            String response = ApiResponse.getResponse(API.API_GET_LEVELS, formBody);
            return new Json().fromJson(ArrayList.class, Level.class, response);
        }
    }

    public static class Tiles {
        public static ArrayList getTiles() throws Exception {
            RequestBody formBody = new FormBody.Builder().build();
            String response = ApiResponse.getResponse(API.API_GET_TILES, formBody);
            return new Json().fromJson(ArrayList.class, Tile.class, response);
        }
    }

    public static class SkinsScreen {
        public static ArrayList getSkins() throws Exception {
            RequestBody formBody = new FormBody.Builder()
                    .add("idUserOrUsername", String.valueOf(GameData.Player.PLAYER_ID))
                    .build();
            String response = ApiResponse.getResponse(API.API_GET_SKINS, formBody);
            return new Json().fromJson(ArrayList.class, Skin.class, response);
        }
    }

    public static class Settings {

    }

    public static class GameScreen {
        public static void finishedLevel() {

        }
    }
}