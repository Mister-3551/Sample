package core.screens.menuscreen;

import com.badlogic.gdx.utils.Json;
import core.API;
import core.ApiResponse;
import core.GameData;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MenuConnection {

    public void getPlayerBasicData() throws Exception {

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