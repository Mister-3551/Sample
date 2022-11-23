package core.screens;

import core.Constants;

public class ScreenChanger {

    public void changeScreen(String screenName, String... level) {
        switch (screenName) {
            case "LoginScreen":
                Constants.INSTANCE.setScreen(new LoginScreen());
                return;
            case "MenuScreen":
                Constants.INSTANCE.setScreen(new MenuScreen());
                return;
            case "SettingsScreen":
                Constants.INSTANCE.setScreen(new SettingsScreen());
                return;
            case "LevelsScreen":
                Constants.INSTANCE.setScreen(new LevelsScreen());
                return;
            case "ShopScreen":
                Constants.INSTANCE.setScreen(new ShopScreen());
                return;
            case "GameScreen":
                Constants.INSTANCE.setScreen(new GameScreen(Constants.ORTHOGRAPHIC_CAMERA, level));
                return;
        }
    }
}