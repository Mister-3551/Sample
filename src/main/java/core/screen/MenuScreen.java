package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import core.Constants;
import core.loginscreen.LoginConnection;
import core.xml.XmlFile;

public class MenuScreen extends ScreenAdapter {

    private Label label;
    private TextButton play;
    private TextButton settings;
    private TextButton signOut;
    private Table table;
    private Skin skin;
    private Stage stage;

    public MenuScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        label = new Label("", skin);
        play = new TextButton("Play", skin);
        settings = new TextButton("Settings", skin);
        signOut = new TextButton("Sign Out", skin);
        table = new Table();

        createStructure();

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
        label.setAlignment(Align.center);

        table.setFillParent(true);
        table.setDebug(false);

        table.add(label).width(400).height(50).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(play).width(400).height(50).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settings).width(400).height(50).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(signOut).width(400).height(50).fillX().uniformX();

        play.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("GameScreen");
            }
        });
        settings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                label.setText("Settings");
            }
        });
        signOut.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                label.setText("Sign Out");
                new LoginConnection().signOut();
                Constants.INSTANCE.setScreen(new LoginScreen());
            }
        });
    }
}
