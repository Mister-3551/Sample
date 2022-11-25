package core.settingsscreen;

import com.badlogic.gdx.utils.Json;
import core.ApiResponse;
import core.GameData;
import core.settingsscreen.objects.Settings;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static core.API.*;

public class SettingsConnection {

    public SettingsConnection() {}

    public Settings getControls() {
        Settings settings = null;
        try {
            RequestBody formBody = new FormBody.Builder()
                    .add("gameToken", GameData.Player.PLAYER_GAME_TOKEN)
                    .build();
            String response = ApiResponse.getResponse(API_GET_SETTINGS, formBody);
            if (!response.isBlank())  {
                settings = new Json().fromJson(Settings.class, Settings.class, response);
                setControls(settings);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return settings;
    }

    public void setControls(Settings settings) {
        GameData.Player.PLAYER_KEY_LEFT = settings.getKeyLeft();
        GameData.Player.PLAYER_KEY_RIGHT = settings.getKeyRight();
        GameData.Player.PLAYER_KEY_JUMP = settings.getKeyJump();
        GameData.Player.PLAYER_KEY_SHOOT = settings.getKeyShoot();
        GameData.Player.PLAYER_SETTINGS = settings;
    }
}