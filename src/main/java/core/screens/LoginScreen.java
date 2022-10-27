package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.Constants;
import core.loginscreen.LoginConnection;

public class LoginScreen extends ScreenAdapter {

    private Label errorLabel;
    private TextField username, password;
    private TextButton gameName, empty, signIn, signUp, forgotPassword, exit;
    private Table table, navigation, form;
    private Skin skin;
    private Stage stage;

    public LoginScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        errorLabel = new Label("", skin);
        gameName = new TextButton("Game Name", skin);
        empty = new TextButton("", skin);
        username = new TextField("", skin);
        password = new TextField("", skin);
        signIn = new TextButton("Sign In", skin);
        signUp = new TextButton("Sign Up", skin);
        forgotPassword = new TextButton("Forgot Password", skin);
        exit = new TextButton("Exit", skin);
        table = new Table();
        navigation = new Table();
        form = new Table();

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

        table.setFillParent(true);

        errorLabel.setAlignment(Align.center);

        navigation.add(gameName).fill(true).width(200.0f).height(50.0f);
        navigation.add(empty).fill(true).growX();
        navigation.add(signUp).fill(true).width(200.0f).height(50.0f);
        navigation.add(forgotPassword).fill(true).width(200.0f).height(50.0f);

        table.add(navigation).growX();

        table.row();

        username.setAlignment(Align.center);
        username.setMessageText("Enter Username");

        password.setPasswordMode(true);
        password.setPasswordCharacter('•');
        password.setAlignment(Align.center);
        password.setMessageText("Enter Password");

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        var background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(background);

        form.add(errorLabel).padBottom(10).fill(true).height(50).growX();
        form.row();
        form.add(username).padBottom(10.0f).fill(true).width(400.0f).height(50.0f);
        form.row();
        form.add(password).padBottom(10.0f).fill(true).width(400.0f).height(50.0f);
        form.row();
        form.add(signIn).padBottom(10.0f).fill(true).width(400.0f).height(50.0f);
        form.row();
        form.add(exit).fill(true).width(400.0f).height(50.0f);

        table.add(form).fill(true).growX().growY();

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