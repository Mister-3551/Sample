package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.Constants;
import core.loginscreen.LoginConnection;
import core.xml.XmlFile;

public class LoginScreen extends ScreenAdapter {

    private Label errorLabel;
    private TextField username;
    private TextField password;
    private TextButton signIn, signUp, forgotPassword, exit;
    private Table table;
    private Skin skin;
    private Stage stage;

    public LoginScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        errorLabel = new Label("", skin);
        username = new TextField("", skin);
        password = new TextField("", skin);
        signIn = new TextButton("Sign In", skin);
        signUp = new TextButton("Sign Up", skin);
        forgotPassword = new TextButton("Forgot Password", skin);
        exit = new TextButton("Exit", skin);
        table = new Table();

        createStructure();

        table.setDebug(false);

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

    private void createStructure() {

        table.setBackground(new TextureRegionDrawable(new Texture("backgrounds/login-background.png")));
        table.setFillParent(true);

        errorLabel.setAlignment(Align.center);
        table.add(errorLabel).padBottom(10.0f).colspan(2);

        table.row();
        username.setAlignment(Align.center);
        username.setMessageText("Enter Username");
        table.add(username).padBottom(10.0f).fill(true).width(400.0f).height(50.0f).colspan(2);

        table.row();
        password.setPasswordMode(true);
        password.setPasswordCharacter('•');
        password.setAlignment(Align.center);
        password.setMessageText("Enter Password");
        table.add(password).padBottom(10.0f).fill(true).width(400.0f).height(50.0f).colspan(2);

        table.row();
        table.add(signIn).padBottom(10.0f).fill(true).width(400.0f).height(50.0f).colspan(2);

        table.row();
        table.add(signUp).padBottom(10.0f).padRight(5.0f).fill(true).width(195.0f).height(50.0f).colspan(1);
        table.add(forgotPassword).padBottom(10.0f).padLeft(5.0f).fill(true).width(195.0f).height(50.0f).colspan(1);

        table.row();
        table.add(exit).padBottom(10.0f).fill(true).width(400.0f).height(50.0f).colspan(2);

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        username.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                errorLabel.setText("");
                return false;
            }
        });
        password.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                errorLabel.setText("");
                return false;
            }
        });

        signIn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (username.getText().isEmpty() || password.getText().isEmpty())
                    setErrorLabelText("Fields can not be empty");
                else {
                    if (new LoginConnection().checkIfUserExists(username.getText(), password.getText()))
                        new ScreenChanger().changeScreen("MenuScreen");
                    else setErrorLabelText("Wrong username or password");
                }
            }
        });

        signUp.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://localhost:3000/signup");
            }
        });

        forgotPassword.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://localhost:3000/forgotpassword");
            }
        });

        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void setErrorLabelText(String error) {
        errorLabel.setText(error);
    }
}