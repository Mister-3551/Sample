package core;

import com.badlogic.gdx.utils.Json;
import core.objects.Tile;
import core.screens.levelsscreen.Level;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class ResponseDataConnection {

    public static class Levels {
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
            RequestBody formBody = new FormBody.Builder()
                    .add("idUser", String.valueOf(GameData.Player.PLAYER_ID))
                    .build();
            String response = ApiResponse.getResponse(API.API_GET_TILES, formBody);
            return new Json().fromJson(ArrayList.class, Tile.class, response);
        }
    }

    public static class Settings {

    }
}
