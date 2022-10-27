package core.screens;

import core.Constants;

public class ScreenChanger {

    public void changeScreen(String screenName) {
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
            case "GameScreen":
                Constants.INSTANCE.setScreen(new GameScreen(Constants.orthographicCamera));
                return;
        }
    }
}