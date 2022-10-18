package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.screen.GameScreen;
import core.screen.LoginScreen;

public class Boot extends Game {

    private static Boot INSTANCE;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public Boot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
        //setScreen(new GameScreen(orthographicCamera));
        setScreen(new LoginScreen(INSTANCE, orthographicCamera));
    }
}