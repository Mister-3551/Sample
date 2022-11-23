package core.levelscreen;

import com.badlogic.gdx.utils.Json;
import core.ApiResponse;
import core.PlayerData;
import core.levelscreen.objects.Level;
import java.net.URL;
import java.util.ArrayList;

import static core.API.API_GET_LEVELS;

public class LevelConnection {

    private URL url;

    public LevelConnection() {}

    public ArrayList<Level> levelsList() {
        ArrayList<Level> levels = new ArrayList<>();
        try {
            url = new URL(String.format(API_GET_LEVELS, PlayerData.PLAYER_GAME_TOKEN));
            String response = ApiResponse.getResponse(url);
            if (!response.isBlank()) return new Json().fromJson(ArrayList.class, Level.class, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return levels;
    }
}