package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.loginscreen.LoginConnection;
import core.screens.ScreenChanger;

public class Boot extends Game {

    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public Boot() {
        Constants.INSTANCE = this;
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
        Constants.ORTHOGRAPHIC_CAMERA = orthographicCamera;
        new ScreenChanger().changeScreen(isSignIn());
    }

    private String isSignIn() {
        if (!new LoginConnection().checkIfUserSignIn()) return "LoginScreen";
        else return "MenuScreen";
    }
}