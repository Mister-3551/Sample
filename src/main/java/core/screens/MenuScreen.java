package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.Constants;
import core.levelscreen.LevelConnection;
import core.screens.navigation.NavigationBar;

public class MenuScreen extends ScreenAdapter {

    private Label label;
    private TextButton multiplayer, localMultiplayer, play, levels, settings;
    private Table table, form, combine;
    private Image playerSkin;
    private Skin skin;
    private Stage stage;

    public MenuScreen() {
        Constants.LEVEL_LIST = new LevelConnection().levelsList();
        Constants.CURRENT_LEVEL = Constants.LEVEL_LIST.size();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        label = new Label("", skin);
        multiplayer = new TextButton("Multiplayer", skin);
        localMultiplayer = new TextButton("Local Multiplayer", skin);
        play = new TextButton("Play - Level " + Constants.LEVEL_LIST.size(), skin);
        levels = new TextButton("Levels", skin);
        settings = new TextButton("Settings", skin);
        table = new Table();
        form = new Table();
        combine = new Table();
        playerSkin = new Image(new Texture(Gdx.files.internal("player/player_normal/player_normal.png")));

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

        table.setFillParent(true);

        table.add(new NavigationBar().menuNavigationBar()).growX();

        table.row();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        var background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(background);

        form.add(play).padBottom(10.0f).fill(true).width(400.0f).height(50.0f);
        form.row();
        form.add(levels).padBottom(10.0f).fill(true).width(400.0f).height(50.0f);
        form.row();
        form.add(settings).padBottom(10.0f).fill(true).width(400.0f).height(50.0f);

        combine.add(playerSkin).width(400).height(400).growX();
        combine.add(form).width(400).growX();

        table.add(combine).fill(true).growX().growY();

        play.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("GameScreen");
            }
        });
        levels.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("LevelsScreen");
            }
        });
        settings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("SettingsScreen");
            }
        });
    }
}
