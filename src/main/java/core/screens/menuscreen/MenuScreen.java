package core.screens.menuscreen;

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
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.GameData;
import core.screens.ScreenChanger;
import core.screens.navigation.NavigationBar;

public class MenuScreen extends ScreenAdapter {

    private Label label;
    private TextButton play, levels, settings;
    private Table table, form, combine;
    private Image playerSkin;
    private Skin skin;
    private Stage stage;

    public MenuScreen() {
        playerBasicData();
        stage = new Stage(new FitViewport(GameData.SCREEN_WIDTH, GameData.SCREEN_HEIGHT));
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));
        label = new Label("", skin);
        play = new TextButton("Play - Level " + GameData.CURRENT_LEVEL, skin);
        levels = new TextButton("Levels", skin);
        settings = new TextButton("Settings", skin);
        table = new Table();
        form = new Table();
        combine = new Table();
        playerSkin = new Image(new Texture(Gdx.files.internal("player/player_normal/player_normal.png")));

        createStructure();

        GameData.Player.PLAYER_KEY_JUMP = 51;
        GameData.Player.PLAYER_KEY_LEFT = 29;
        GameData.Player.PLAYER_KEY_RIGHT = 32;
        GameData.Player.PLAYER_KEY_SHOOT = 0;

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
        stage.setViewport(new FitViewport(width, height));
        stage.getViewport().update(width, height, true);
        stage.addActor(table);
    }

    private void playerBasicData() {
        try {
            new MenuConnection().getPlayerBasicData();
        } catch (Exception e) {
            //TODO make a popup for trouble
        }
    }

    private void createStructure() {

        Pixmap pm = new Pixmap(Gdx.files.internal(GameData.Skins.Cursor.SWORD_CURSOR));
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
                var currentLevel = "map" + GameData.CURRENT_LEVEL + ".tmx";
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