package core.settingsscreen;

import com.badlogic.gdx.utils.Json;
import core.ApiResponse;
import core.PlayerData;
import core.settingsscreen.objects.Settings;

import java.net.URL;
import java.util.ArrayList;

import static core.API.*;

public class SettingsConnection {
    private URL url;

    public SettingsConnection() {}

    public Settings getControls() {
        Settings settings = null;
        try {
            url = new URL(String.format(API_GET_SETTINGS, PlayerData.PLAYER_GAME_TOKEN));
            String response = ApiResponse.getResponse(url);
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
        PlayerData.PLAYER_KEY_LEFT = settings.getKeyLeft();
        PlayerData.PLAYER_KEY_RIGHT = settings.getKeyRight();
        PlayerData.PLAYER_KEY_JUMP = settings.getKeyJump();
        PlayerData.PLAYER_KEY_SHOOT = settings.getKeyShoot();
        PlayerData.PLAYER_SETTINGS = settings;
    }
}