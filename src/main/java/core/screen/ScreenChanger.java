package core.screen;

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
                return;
            case "GameScreen":
                Constants.INSTANCE.setScreen(new GameScreen(Constants.orthographicCamera));
                return;
        }
    }
}