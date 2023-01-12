package core.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.GameData;
import core.screens.endscreen.EndScreen;
import core.screens.gamescreen.GameScreen;
import core.screens.levelsscreen.LevelsScreen;
import core.screens.menuscreen.MenuScreen;
import core.screens.settingsscreen.SettingsScreen;
import core.screens.signinscreen.SignInScreen;
import core.screens.skinscreen.SkinScreen;

public class ScreenChanger {

    private Boot instance;
    private OrthographicCamera camera;

    public void changeScreen(String screenName, String... level) {
        this.instance = GameData.INSTANCE;
        this.camera = GameData.GameScreen.Camera.ORTHOGRAPHIC_CAMERA;

        switch (screenName) {
            case "LoginScreen" -> instance.setScreen(new SignInScreen());
            case "MenuScreen" -> instance.setScreen(new MenuScreen());
            case "SettingsScreen" -> instance.setScreen(new SettingsScreen());
            case "LevelsScreen" -> instance.setScreen(new LevelsScreen());
            case "SkinScreen" -> instance.setScreen(new SkinScreen());
            case "GameScreen" -> instance.setScreen(new GameScreen(camera, level));
            case "EndScreen" -> instance.setScreen(new EndScreen());
        }
    }
}