package core.screens.levelsscreen;

import com.badlogic.gdx.utils.Json;
import core.API;
import core.ApiResponse;
import core.GameData;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class LevelsConnection {

    public ArrayList<Level> getLevels() throws Exception {

        RequestBody formBody = new FormBody.Builder()
                .add("idUserOrUsername", String.valueOf(GameData.Player.PLAYER_ID))
                .build();
        String response = ApiResponse.getResponse(API.API_GET_LEVELS, formBody);

        return new Json().fromJson(ArrayList.class, Level.class, response);
    }
}