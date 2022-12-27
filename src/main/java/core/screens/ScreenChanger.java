package core.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.GameData;

public class ScreenChanger {

    private Boot instance;
    private OrthographicCamera camera;

    public void changeScreen(String screenName, String... level) {
        this.instance = GameData.INSTANCE;
        this.camera = GameData.GameScreen.Camera.ORTHOGRAPHIC_CAMERA;

        switch (screenName) {
            case "LoginScreen":
                instance.setScreen(new LoginScreen());
                return;
            case "MenuScreen":
                instance.setScreen(new MenuScreen());
                return;
            case "SettingsScreen":
                instance.setScreen(new SettingsScreen());
                return;
            case "LevelsScreen":
                instance.setScreen(new LevelsScreen());
                return;
            case "InventoryScreen":
                instance.setScreen(new InventoryScreen());
                return;
            case "GameScreen":
                instance.setScreen(new GameScreen(camera, level));
                return;
        }
    }
}