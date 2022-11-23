package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.backends.lwjgl3.DefaultLwjgl3Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.Constants;
import core.levelscreen.LevelConnection;
import core.levelscreen.objects.Level;
import core.loginscreen.LoginConnection;
import core.screens.navigation.NavigationBar;
import core.settingsscreen.SettingsConnection;
import core.settingsscreen.objects.Settings;
import core.shopscreen.ShopConnection;
import core.shopscreen.objects.Unit;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Locale;

import static com.badlogic.gdx.Gdx.input;

public class InventoryScreen extends ScreenAdapter {
    private TextureRegionDrawable background;
    private Image image;
    private Skin skin;
    private Stage stage;

    private Table stageTable;

    private ArrayList<Unit> unitsList;
    private ArrayList<Level> levelList;

    public InventoryScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));

        stageTable = new Table();

        unitsList = new ShopConnection().unitsList();
        levelList = new LevelConnection().levelsList();

        createStructure();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        stageTable.setBackground(background);

        stage.addActor(stageTable);
        //stage.setScrollFocus(scrollPane);
        input.setInputProcessor(stage);
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

    /*private void createStructure() {
        //Inventory
        Table bottom = new Table();
        Table frame = new Table();

        Table scrollPaneTable = new Table();
        scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        Label title = new Label("Inventory", skin.get("big-title", Label.LabelStyle.class));

        int index = 0;
        for (Unit unit : unitsList) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();
            TextButton buy = new TextButton("Buy", skin);

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label(unit.getName(), skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            product.setBackground(setBackground(Color.GREEN));
            frame.setBackground(setBackground(Color.RED));

            product.add(levelName).pad(10, 10, 10, 10).growX();
            product.row();
            product.add(image).pad(0, 10, 10, 10).width(100).height(100);
            product.row();
            product.add(buy).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 3 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(0, 10, 10, 10).width(200).growY();
        }

        stageTable.setFillParent(true);

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title);

        stageTable.row();
        stageTable.add(scrollPane).growY();
        stageTable.row();
        stageTable.add(new Table()).growY();
    } */

    /*private void createStructure() {
        //Level
        Table scrollPaneTable = new Table();
        scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        Label title = new Label("Select Level", skin.get("big-title", Label.LabelStyle.class));

        int index = 0;

        for (int i = 0; i < 10; i++) {
            for (Level level : levelList) {
                image = new Image(new Texture(Constants.PLAYER_NORMAL));
                image.setAlign(Align.center);

                Table product = new Table();
                TextButton play = new TextButton("Play", skin);

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
                product.add(play).pad(0, 0, 0, 0).height(50).growX();

                if (index++ % 3 == 0) scrollPaneTable.row();
                scrollPaneTable.add(product).pad(0, 10, 10, 10).width(250).growY();
            }
        }

        stageTable.setFillParent(true);

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title);

        stageTable.row();
        stageTable.add(scrollPane).growY();
        stageTable.row();
        stageTable.add(new Table()).growY();
    } */

    /*private void createStructure() {
        //Shop

        Table frame = new Table();
        frame.add(unitsTable()).growX().growY();

        Table unitMapTable = new Table();

        Table unitMap = new Table();
        TextButton unitButton = new TextButton("Units", skin);
        TextButton mapButton = new TextButton("Maps", skin);

        unitMapTable.add(unitButton).pad(0, 0, 0, 5).height(50).growX();
        unitMapTable.add(mapButton).pad(0, 5, 0, 0).height(50).growX();

        unitMap.add(unitMapTable).pad(0, 0, 10, 0).growX();
        unitMap.row();
        unitMap.add(frame).growX();

        Label title = new Label("Shop", skin.get("big-title", Label.LabelStyle.class));

        stageTable.setFillParent(true);

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title).pad(0, 0, 10, 0);
        stageTable.row();
        stageTable.add(unitMap);
        //stageTable.row();
        //stageTable.add(frame).pad(10, 0, 0, 0).growY();
        stageTable.row();
        stageTable.add(new Table()).growY();

        unitButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(unitsTable()).growY();
            }
        });
        mapButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(mapsTable()).growY();
            }
        });
    }

    private ScrollPane unitsTable() {
        Table scrollPaneTable = new Table();
        scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
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

            Label levelName = new Label(unit.getName(), skin);
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

    private ScrollPane mapsTable() {
        Table scrollPaneTable = new Table();
        scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        ArrayList<Integer> mapsList = new ArrayList<>();
        mapsList.add(1);
        mapsList.add(2);
        mapsList.add(3);
        mapsList.add(4);

        int index = 0;
        for (int integer : mapsList) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();
            TextButton buy = new TextButton("Buy", skin);

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label("Map " + integer, skin);
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
    } */

    /*private void createStructure() {
        //Settings
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        Label title = new Label("Settings", skin.get("big-title", Label.LabelStyle.class));

        String[] settings = {"Music", "Sound Effect"};

        int index = 0;

        for (String setting : settings) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();
            TextButton play = new TextButton("Play", skin);

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label(setting, skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            Slider musicSlider = new Slider(0.0f, 100.0f, 1.0f, false, skin);

            product.setBackground(setBackground(Color.GREEN));

            product.add(image).pad(10, 10, 10, 10).width(50).height(50);
            product.add(levelName).pad(10, 10, 10, 10).growX();
            product.add(musicSlider).pad(10, 10, 10, 10).height(50).growX();

            if (index++ % 2 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(10, 10, 10, 10).height(70).width(400);
        }

        stageTable.setFillParent(true);

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title);

        stageTable.row();
        stageTable.add(scrollPane).width(Constants.SCROLL_PANE_SIZE);
        stageTable.row();
        stageTable.add(new Table()).growY();
    } */

    private void createStructure() {
        //Settings

        Table frame = new Table();
        frame.add(controlTable()).growX().growY();

        Table unitMapTable = new Table();

        Table unitMap = new Table();
        TextButton controlSettings = new TextButton("Control Settings", skin);
        TextButton volumeSettings = new TextButton("Volume Settings", skin);
        TextButton otherSettings = new TextButton("Other Settings", skin);

        unitMapTable.add(controlSettings).pad(0, 0, 0, 5).height(50).growX();
        unitMapTable.add(volumeSettings).pad(0, 5, 0, 0).height(50).growX();
        unitMapTable.add(otherSettings).pad(0, 5, 0, 0).height(50).growX();

        unitMap.add(unitMapTable).pad(0, 0, 10, 0).growX();
        unitMap.row();
        unitMap.add(frame).growX();

        Label title = new Label("Settings", skin.get("big-title", Label.LabelStyle.class));

        stageTable.setFillParent(true);
        stageTable.setBackground(setBackground(Color.LIGHT_GRAY));

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title).pad(0, 0, 10, 0);
        stageTable.row();
        stageTable.add(unitMap).width(Constants.SCROLL_PANE_SIZE);
        stageTable.row();
        stageTable.add(new Table()).growY();

        controlSettings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(controlTable()).width(Constants.SCROLL_PANE_SIZE).growY();
            }
        });
        volumeSettings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(volumeTable()).width(Constants.SCROLL_PANE_SIZE).growY();
            }
        });
        otherSettings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(otherTable()).width(Constants.SCROLL_PANE_SIZE).growY();
            }
        });
    }

    private ScrollPane controlTable() {
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        //ArrayList<Settings> controlsList = new SettingsConnection().controlsList();
        ArrayList<Settings> controlsList = new ArrayList<>();

        int index = 0;
        for (Settings control : controlsList) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label("control.getName(", skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            String keyName = GLFW.glfwGetKeyName(DefaultLwjgl3Input.getGlfwKeyCode(Integer.parseInt("2")), 0);
            if (keyName != null) keyName = keyName.toUpperCase(Locale.ROOT);

            TextButton textButton = new TextButton(keyName, skin);

            //enterField.setMaxLength(1);

            product.setBackground(setBackground(Color.GREEN));

            product.add(image).pad(10, 10, 10, 10).width(50).height(50);
            product.add(levelName).pad(10, 10, 10, 10).growX();
            product.add(textButton).pad(10, 10, 10, 10).width(150).height(50);

            if (index++ % 2 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(10, 10, 10, 10).height(70).width(400);

            textButton.addListener(new InputListener() {
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    textButton.setText(String.valueOf(Input.Keys.ENTER));
                }
            });
        }
        return scrollPane;
    }

    private ScrollPane volumeTable() {
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        //ArrayList<Settings> volumeList = new SettingsConnection().volumeList();
        ArrayList<Settings> volumeList = new ArrayList<>();

        int index = 0;
        for (Settings volume : volumeList) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();
            TextButton play = new TextButton("Play", skin);

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label("volume.getName()", skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            Slider musicSlider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
            musicSlider.setVisualPercent(Float.parseFloat("2"));

            product.setBackground(setBackground(Color.GREEN));

            product.add(image).pad(10, 10, 10, 10).width(50).height(50);
            product.add(levelName).pad(10, 10, 10, 10).growX();
            product.add(musicSlider).pad(10, 10, 10, 10).height(50).growX();

            if (index++ % 2 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(10, 10, 10, 10).height(70).width(400);
        }
        return scrollPane;
    }

    private ScrollPane otherTable() {
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        //ArrayList<Settings> otherList = new SettingsConnection().otherList();
        ArrayList<Settings> otherList = new ArrayList<>();

        String[] settings = {"Language", "Sign Out", "Profile"};

        String[] items = {"English", "Slovenian", "Russian"};

        int index = 0;
        for (String setting : settings) {
            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();

            image = new Image(new Texture(Constants.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label(setting, skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            SelectBox<String> selectBox = new SelectBox(skin);
            selectBox.setAlignment(Align.center);
            selectBox.setItems(items);

            TextButton signOut = new TextButton("Sign Out", skin);
            TextButton deleteProfile = new TextButton("Delete", skin);

            product.setBackground(setBackground(Color.GREEN));

            product.add(image).pad(10, 10, 10, 10).width(50).height(50);
            product.add(levelName).pad(10, 10, 10, 10).growX();

            if (index == 0) product.add(selectBox).pad(10, 10, 10, 10).height(50).growX();
            else if (index == 1) product.add(signOut).pad(10, 10, 10, 10).height(50).growX();
            else if (index == 2) product.add(deleteProfile).pad(10, 10, 10, 10).height(50).growX();

            if (index++ % 2 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(10, 10, 10, 10).height(70).width(400);

            signOut.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    //new LoginConnection().signOut();
                    new ScreenChanger().changeScreen("LoginScreen");
                }
            });
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