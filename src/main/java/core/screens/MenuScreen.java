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
import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonParser;
import core.Constants;
import core.PlayerData;
import core.levelscreen.LevelConnection;
import core.levelscreen.objects.Level;
import core.screens.navigation.NavigationBar;
import core.settingsscreen.SettingsConnection;
import core.settingsscreen.objects.Settings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuScreen extends ScreenAdapter {

    private Label label;
    private TextButton multiplayer, localMultiplayer, play, levels, settings;
    private Table table, form, combine;
    private Image playerSkin;
    private Skin skin;
    private Stage stage;
    private ArrayList<Level> levelsList;

    public MenuScreen() {
        levelsList = new LevelConnection().levelsList();
        PlayerData.CURRENT_LEVEL = (int) levelsList.stream().filter(level -> level.getCompleted() == 1 || level.getCompleted() == 2).count();
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        label = new Label("", skin);
        multiplayer = new TextButton("Multiplayer", skin);
        localMultiplayer = new TextButton("Local Multiplayer", skin);
        play = new TextButton("Play - Level " + PlayerData.CURRENT_LEVEL, skin);
        levels = new TextButton("Levels", skin);
        settings = new TextButton("Settings", skin);
        table = new Table();
        form = new Table();
        combine = new Table();
        playerSkin = new Image(new Texture(Gdx.files.internal("player/player_normal/player_normal.png")));

        new SettingsConnection().getControls();
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

        Pixmap pm = new Pixmap(Gdx.files.internal("sword/sword.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();

        table.setFillParent(true);

        table.add(new NavigationBar().menuNavigationBar()).growX();

        table.row();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        var background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(background);

        form.add(play).padBottom(10.0f).width(400.0f).height(50.0f);
        form.row();
        form.add(levels).padBottom(10.0f).width(400.0f).height(50.0f);
        form.row();
        form.add(settings).padBottom(10.0f).width(400.0f).height(50.0f);

        combine.add(playerSkin).width(400).height(400).growX();
        combine.add(form).width(400).growX();

        table.add(combine).growX().growY();

        play.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                var currentLevel = levelsList.get(PlayerData.CURRENT_LEVEL - 1).getMap();
                new ScreenChanger().changeScreen("GameScreen", currentLevel);
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