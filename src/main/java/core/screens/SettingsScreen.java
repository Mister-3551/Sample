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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.GameData;
import core.screens.levelsscreen.Level;
import core.screens.navigation.NavigationBar;
import core.settingsscreen.objects.Settings;
import core.inventoryscreen.InventoryConnection;
import core.inventoryscreen.objects.Unit;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Locale;

import static com.badlogic.gdx.Gdx.input;

public class SettingsScreen extends ScreenAdapter {
    private TextureRegionDrawable background;
    private Image image;
    private Skin skin;
    private Stage stage;

    private Table stageTable;

    private ArrayList<Unit> unitsList;
    private ArrayList<Level> levelList;
    private Settings playerSettings;

    private Slider musicSlider;
    private Slider soundEffectSlider;
    private SelectBox<String> selectBox;

    public SettingsScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));

        stageTable = new Table();

        unitsList = new InventoryConnection().inventoryList();
        //levelList = new LevelConnection().levelsList();
        playerSettings = GameData.Player.PLAYER_SETTINGS;

        musicSlider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        soundEffectSlider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        selectBox = new SelectBox(skin);

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
        stageTable.add(unitMap).width(GameData.LevelScreen.SCROLL_PANE_SIZE);
        stageTable.row();
        stageTable.add(new Table()).growY();

        controlSettings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(controlTable()).width(GameData.LevelScreen.SCROLL_PANE_SIZE).growY();
            }
        });
        volumeSettings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(volumeTable()).width(GameData.LevelScreen.SCROLL_PANE_SIZE).growY();
            }
        });
        otherSettings.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                frame.clear();
                frame.add(otherTable()).width(GameData.LevelScreen.SCROLL_PANE_SIZE).growY();
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

        ArrayList<Settings.Setting> setting = new ArrayList<>();
        setting.add(new Settings.Setting("KEY LEFT", playerSettings.getKeyLeft()));
        setting.add(new Settings.Setting("KEY RIGHT", playerSettings.getKeyRight()));
        setting.add(new Settings.Setting("KEY JUMP", playerSettings.getKeyJump()));
        setting.add(new Settings.Setting("KEY SHOOT", playerSettings.getKeyShoot()));

        int index = 0;
        for (Settings.Setting control : setting) {
            image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();

            image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label controlName = new Label(control.getName(), skin);
            controlName.setAlignment(Align.center);
            controlName.setColor(Color.RED);

            String keyName = GLFW.glfwGetKeyName(DefaultLwjgl3Input.getGlfwKeyCode((int) control.getCode()), 0);
            if (keyName != null) keyName = keyName.toUpperCase(Locale.ROOT);

            TextButton textButton = new TextButton(keyName, skin);

            //enterField.setMaxLength(1);

            product.setBackground(setBackground(Color.GREEN));

            product.add(image).pad(10, 10, 10, 10).width(50).height(50);
            product.add(controlName).pad(10, 10, 10, 10).growX();
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

        ArrayList<Settings.Setting> setting = new ArrayList<>();
        setting.add(new Settings.Setting("MUSIC", playerSettings.getMusic()));
        setting.add(new Settings.Setting("SOUND EFFECT", playerSettings.getSoundEffect()));

        image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
        image.setAlign(Align.center);

        Table musicProduct = new Table();
        Table soundEffectProduct = new Table();

        image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
        image.setAlign(Align.center);

        Label musicLabel = new Label("Music", skin);
        musicLabel.setAlignment(Align.center);
        musicLabel.setColor(Color.RED);

        Label soundEffectLabel = new Label("SoundEffect", skin);
        soundEffectLabel.setAlignment(Align.center);
        soundEffectLabel.setColor(Color.RED);

        musicSlider.setVisualPercent(setting.get(0).getCode());
        soundEffectSlider.setVisualPercent(setting.get(1).getCode());

        musicProduct.setBackground(setBackground(Color.GREEN));

        musicProduct.add(image).pad(10, 10, 10, 10).width(50).height(50);
        musicProduct.add(musicLabel).pad(10, 10, 10, 10).growX();
        musicProduct.add(musicSlider).pad(10, 10, 10, 10).height(50).growX();

        soundEffectProduct.setBackground(setBackground(Color.GREEN));

        image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
        image.setAlign(Align.center);

        soundEffectProduct.add(image).pad(10, 10, 10, 10).width(50).height(50);
        soundEffectProduct.add(soundEffectLabel).pad(10, 10, 10, 10).growX();
        soundEffectProduct.add(soundEffectSlider).pad(10, 10, 10, 10).height(50).growX();

        scrollPaneTable.add(musicProduct).pad(10, 10, 10, 10).height(70).width(400);
        scrollPaneTable.add(soundEffectProduct).pad(10, 10, 10, 10).height(70).width(400);
        scrollPaneTable.row();

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

        ///ArrayList<Settings> otherList = new SettingsConnection().otherList();
        ArrayList<Settings> otherList = new ArrayList<>();

        String[] settings = {"Language", "Sign Out", "Profile"};

        int index = 0;
        for (String setting : settings) {
            image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Table product = new Table();

            image = new Image(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
            image.setAlign(Align.center);

            Label levelName = new Label(setting, skin);
            levelName.setAlignment(Align.center);
            levelName.setColor(Color.RED);

            String[] items = {"English", "Slovenian", "Russian"};
            selectBox.setAlignment(Align.center);
            selectBox.setItems(items);
            selectBox.setSelected("w");

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

            deleteProfile.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.net.openURI("http://localhost:3000/deleteprofile");
                }
            });

            signOut.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    //if (new SignInConnection().userSignOut()) new ScreenChanger().changeScreen("LoginScreen");
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