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
import com.badlogic.gdx.utils.Align;
import core.Constants;
import core.GameData;
import core.levelscreen.LevelConnection;
import core.levelscreen.objects.Level;
import core.screens.navigation.NavigationBar;
import core.shopscreen.ShopConnection;
import core.shopscreen.objects.Unit;

import java.util.ArrayList;

public class ShopScreen extends ScreenAdapter {

    private Table stageTable;
    private Image image;
    private ArrayList<Unit> unitsList;
    private ArrayList<Level> levelsList;
    private Skin skin;
    private Stage stage;

    public ShopScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));

        stageTable = new Table();
        unitsList = new ShopConnection().unitsList();
        levelsList = new LevelConnection().levelsList();

        createStructure();

        stage.addActor(stageTable);
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
        //Shop

        Table frame = new Table();
        frame.add(unitsTable()).growX().growY();

        Table unitMapTable = new Table();

        Table unitMap = new Table();
        TextButton unitButton = new TextButton("Units - Skins", skin);
        TextButton mapButton = new TextButton("Maps - Skins", skin);

        unitMapTable.add(unitButton).pad(0, 0, 0, 5).height(50).growX();
        unitMapTable.add(mapButton).pad(0, 5, 0, 0).height(50).growX();

        unitMap.add(unitMapTable).pad(0, 0, 10, 0).growX();
        unitMap.row();
        unitMap.add(frame).growX();

        Label title = new Label("Shop", skin.get("big-title", Label.LabelStyle.class));

        stageTable.setFillParent(true);
        stageTable.setBackground(setBackground(Color.LIGHT_GRAY));

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title).pad(0, 0, 10, 0);
        stageTable.row();
        stageTable.add(unitMap).width(GameData.LevelScreen.SCROLL_PANE_SIZE);
        stageTable.row();
        stageTable.add(new Table()).growY();

        unitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(unitsTable()).width(GameData.LevelScreen.SCROLL_PANE_SIZE).growY();
            }
        });
        mapButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(mapsTable()).width(GameData.LevelScreen.SCROLL_PANE_SIZE).growY();
            }
        });
    }

    private ScrollPane unitsTable() {
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        int index = 0;
        for (Unit unit : unitsList) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();
            TextButton buy = new TextButton("Buy", skin);

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label unitName = new Label(unit.getName(), skin);
            unitName.setAlignment(Align.center);
            unitName.setColor(Color.RED);

            product.setBackground(setBackground(Color.GREEN));

            product.add(unitName).pad(10, 10, 10, 10).growX();
            product.row();
            product.add(image).pad(0, 10, 10, 10).width(100).height(100);
            product.row();
            product.add(buy).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 3 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(0, 10, 10, 10).width(200).growY();
        }
        return scrollPane;
    }

    private ScrollPane mapsTable() {
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        int index = 0;
        for (Level level : levelsList) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();
            TextButton buy = new TextButton("Buy", skin);

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label(level.getName(), skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            product.setBackground(setBackground(Color.GREEN));

            product.add(levelName).pad(10, 10, 10, 10).growX();
            product.row();
            product.add(image).pad(0, 10, 10, 10).width(100).height(100);
            product.row();
            product.add(buy).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 3 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(0, 10, 10, 10).width(200).growY();
        }
        return scrollPane;
    }

    private TextureRegionDrawable setBackground(Color color) {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }
}