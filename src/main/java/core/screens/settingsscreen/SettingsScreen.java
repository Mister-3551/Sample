package core.screens.settingsscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.backends.lwjgl3.DefaultLwjgl3Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.GameData;
import core.screens.levelsscreen.Level;
import core.screens.navigation.NavigationBar;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsScreen extends ScreenAdapter {

    private Table stageTable;
    Texture texture;
    private Skin skin;
    private Stage stage;

    public SettingsScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));
        stageTable = new Table();

        texture = new Texture(GameData.Skins.Player.PLAYER_NORMAL);

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

        Table scrollPaneTable = new Table();
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(scrollPane);

        stageTable.setFillParent(true);
        stageTable.setBackground(setBackground(Color.LIGHT_GRAY));

        Label title = new Label("Settings", skin.get("big-title", Label.LabelStyle.class));

        int index = 0;

        for (int i = 0; i < 10; i++) {

            Label controlName = new Label("Name", skin);
            controlName.setAlignment(Align.center);
            controlName.setColor(Color.RED);

            String keyName = GLFW.glfwGetKeyName(DefaultLwjgl3Input.getGlfwKeyCode((int) 32), 0);
            if (keyName != null) keyName = keyName.toUpperCase(Locale.ROOT);

            TextButton textButton = new TextButton(keyName, skin);

            Table product = new Table();
            product.setBackground(setBackground(Color.GREEN));

            product.add(controlName).pad(10, 10, 10, 10).growX();
            product.add(textButton).pad(10, 10, 10, 10).width(150).height(50);

            if (index++ % 2 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(10, 10, 10, 10).height(70).width(400);
        }

        stageTable.setFillParent(true);

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title);

        stageTable.row();
        stageTable.add(scrollPane).width(GameData.LevelScreen.SCROLL_PANE_SIZE);
        stageTable.row();
        stageTable.add(new Table()).growY();
    }

    private TextureRegionDrawable setBackground(Color color) {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }
}