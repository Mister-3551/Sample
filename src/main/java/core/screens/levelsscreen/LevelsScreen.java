package core.screens.levelsscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.Boot;
import core.GameData;
import core.downloadfile.DownloadFile;
import core.screens.ScreenChanger;
import core.screens.navigation.NavigationBar;
import core.screens.signinscreen.SignInScreen;

import java.net.URL;
import java.util.ArrayList;

public class LevelsScreen extends ScreenAdapter {

    private Table stageTable;
    private ArrayList<Level> levelList;
    Texture texture;
    public static Image image;
    private Skin skin;
    private Stage stage;

    public LevelsScreen(String s) {}

    public LevelsScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));
        stageTable = new Table();

        levelList = getLevels();

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

    public ArrayList<Level> getLevels() {
        ArrayList<Level> levels = new ArrayList<>();
        try {
            levels = new LevelsConnection().getLevels();
        } catch (Exception e) {

        }
        return levels;
    }

    private void createStructure() {

        stageTable.setBackground(setBackground(Color.LIGHT_GRAY));
        //Level
        Table scrollPaneTable = new Table();
        //scrollPaneTable.setBackground(setBackground(Color.LIGHT_GRAY));
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);

        stage.setScrollFocus(scrollPane);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.setScrollingDisabled(true, false);

        Label title = new Label("Level Select", skin.get("big-title", Label.LabelStyle.class));

        int index = 0;
        for (Level level : levelList) {

            try {
                image = new Image(new Texture(GameData.Pictures.LEVEL_PICTURE_DIRECTORY + "/" + level.getPicture()));
                image.setAlign(Align.center);
            } catch (Exception e) {
                //TODO
            }

            Table product = new Table();

            int completed = level.getCompleted();
            String levelType = completed == 0  ? "Locked" : completed == 1 ? "Completed / Play" : "Uncompleted / Play";

            TextButton play = new TextButton(levelType, skin);
            if (completed == 0) play.setTouchable(Touchable.disabled);

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

            int finalIndex = index;
            play.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    GameData.CURRENT_LEVEL = finalIndex;
                    new ScreenChanger().changeScreen("GameScreen", level.getMap());
                }
            });
        }

        image = null;

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