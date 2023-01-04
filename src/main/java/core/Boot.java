package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sun.management.GarbageCollectionNotificationInfo;
import core.screens.ScreenChanger;

public class Boot extends Game {

    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public Boot() {
        GameData.INSTANCE = this;
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
        GameData.GameScreen.Camera.ORTHOGRAPHIC_CAMERA = orthographicCamera;

        //TODO: temporary
        GameData.SCREEN_WIDTH = Gdx.graphics.getWidth();
        GameData.SCREEN_HEIGHT = Gdx.graphics.getHeight();

        new ScreenChanger().changeScreen(isSignIn());
    }

    private String isSignIn() {
        //if (!new LoginConnection().checkIfUserSignIn(new XmlFile().getUserToken())) return "LoginScreen";
        //else return "MenuScreen";
        return "LoginScreen";
    }
}