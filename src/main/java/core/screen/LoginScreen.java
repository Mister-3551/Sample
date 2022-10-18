package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import core.Boot;
import core.loginscreen.LoginConnection;

import java.sql.SQLException;

public class LoginScreen extends ScreenAdapter {

    private Label label;
    private TextField username;
    private TextField password;
    private TextButton signIn;
    private Table table;
    private Skin skin;
    private Stage stage;
    private Boot INSTANCE;
    private OrthographicCamera orthographicCamera;

    public LoginScreen(Boot INSTANCE, OrthographicCamera orthographicCamera) {
        this.INSTANCE = INSTANCE;
        this.orthographicCamera = orthographicCamera;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skins/skin/glassy-ui.json"));
        label = new Label("", skin);
        username = new TextField("", skin);
        password = new TextField("", skin);
        signIn = new TextButton("Sign In", skin);
        table = new Table();

        this.createInputFields();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    private void createInputFields() {
        label.setText("Sing in");
        label.setAlignment(Align.center);
        username.setMessageText("Username");
        password.setMessageText("Password");
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');

        table.setFillParent(true);
        table.setDebug(false);

        table.add(label).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(username).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(password).fillX().uniformX();
        table.row();
        table.add(signIn).fillX().uniformX();

        signIn.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                LoginConnection loginConnection = new LoginConnection();
                if (loginConnection.checkIfUserExists(username.getText(), password.getText())) INSTANCE.setScreen(new GameScreen(orthographicCamera));
            }
        });
    }
}