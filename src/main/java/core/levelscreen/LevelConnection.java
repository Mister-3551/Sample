package core.levelscreen;

import com.badlogic.gdx.utils.Json;
import core.API;
import core.ApiResponse;
import core.PlayerData;
import core.levelscreen.objects.Level;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.net.URL;
import java.util.ArrayList;

public class LevelConnection {

    public LevelConnection() {}

    public ArrayList<Level> levelsList() {
        ArrayList<Level> levels = new ArrayList<>();
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("gameToken", PlayerData.PLAYER_GAME_TOKEN)
                    .build();
            String response = ApiResponse.getResponse(API.API_GET_LEVELS,formBody);
            if (!response.isBlank()) return new Json().fromJson(ArrayList.class, Level.class, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return levels;
    }
}