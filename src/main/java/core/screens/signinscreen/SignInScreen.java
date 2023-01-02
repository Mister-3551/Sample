package core.screens.signinscreen;

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
import core.GameData;
import core.ResponseDataConnection;
import core.downloadfile.DownloadFile;
import core.screens.ScreenChanger;
import core.screens.levelsscreen.Level;

import java.util.ArrayList;

public class SignInScreen extends ScreenAdapter {

    private Label errorLabel;
    private TextField username, password;
    private TextButton gameName, empty, signIn, signUp, forgotPassword, exit;
    private Table table, navigation, form;
    private Skin skin;
    private Stage stage;

    public SignInScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));
        errorLabel = new Label("", skin);
        gameName = new TextButton("Sample Case", skin);
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
        table.setDebug(true);
        form.setDebug(true);

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

        Label title = new Label("Sign In", skin.get("big-title", Label.LabelStyle.class));
        title.setAlignment(Align.center);
        title.setColor(Color.RED);

        Table lower = new Table();
        Table panel = new Table();
        Table form = new Table();

        bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.WHITE);
        bgPixmap.fill();
        background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        panel.setBackground(background);
        bgPixmap.dispose();

        form.add(title).growX().growY();
        form.row();
        form.add(errorLabel).pad(0, 10, 10, 10).height(50).growX();
        form.row();
        form.add(username).pad(0, 10, 10, 10).height(50).growX();
        form.row();
        form.add(password).pad(0, 10, 10, 10).height(50).growX();
        form.row();
        form.add(signIn).pad(0, 10, 10, 10).height(50).growX();

        panel.add(form).growX();

        lower.add(panel).width(400).height(300);
        table.add(lower).growX().growY();

        Pixmap pm = new Pixmap(Gdx.files.internal("sword/sword.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();

        //Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
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
                    try {
                        if (new SignInConnection().userAuthentication(username.getText(), password.getText())) {
                            getDataFromInternet();
                            new ScreenChanger().changeScreen("MenuScreen");
                        } else setErrorLabelText("Wrong username or password");
                    } catch (Exception e) {
                        setErrorLabelText("Something went wrong!");
                        System.out.println(e.getMessage());
                    }
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
                Gdx.net.openURI("http://localhost:3000/resetpassword");
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

    private void getDataFromInternet() {
        try {
        ArrayList<Level> levels = ResponseDataConnection.Levels.getLevels();
        String picturesDirectory = "";
        String mapsDirectory = "";
        for (Level level : levels) {
            picturesDirectory = DownloadFile.getLevelPicture(level.getPicture());
            mapsDirectory = DownloadFile.getLevelMap(level.getMap());
        }
        GameData.Directory.LEVEL_PICTURE_DIRECTORY = picturesDirectory;
        GameData.Directory.LEVEL_MAP_DIRECTORY = mapsDirectory;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}