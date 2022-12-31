package core.screens.signinscreen;

import core.API;
import core.ApiResponse;
import core.GameData;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SignInConnection {

    public boolean userAuthentication(String username, String password) throws Exception {
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

    private void setUserData(String response) throws Exception {
        var object = ApiResponse.getDataFromGameToken(response);
        GameData.Player.PLAYER_ID = (Long) object.get("id");
    }
}