package core.loginscreen;

import core.API;
import core.ApiResponse;
import core.GameData;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginConnection {

    public LoginConnection() {}

    public boolean userAuthentication(String username, String password) {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();
            String response = ApiResponse.getResponse(API.API_SIGN_IN, formBody);
            if (!response.isBlank()) {
                setUserData(response);
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean userSignOut() {
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("gameToken", GameData.Player.PLAYER_GAME_TOKEN)
                    .build();
            String response = ApiResponse.getResponse(API.API_SIGN_OUT, formBody);
            return Boolean.parseBoolean(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private void setUserData(String response) throws Exception {
        var object = ApiResponse.getDataFromGameToken(response);
        GameData.Player.PLAYER_NAME = (String) object.get("name");
        GameData.Player.PLAYER_USERNAME = (String) object.get("username");
        GameData.Player.PLAYER_RANK = (String) object.get("rank");
        GameData.Player.PLAYER_GAME_TOKEN = response;
    }
}