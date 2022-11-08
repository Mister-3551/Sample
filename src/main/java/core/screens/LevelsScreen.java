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
import core.levelscreen.LevelConnection;
import core.levelscreen.objects.Level;
import core.screens.navigation.NavigationBar;

import java.util.ArrayList;

public class LevelsScreen extends ScreenAdapter {

    private Table table, scrollPaneTable, form;

    private ArrayList<Table> levelTables;
    private ArrayList<Level> levelList;
    private ScrollPane scrollPane;
    private Label label;
    private TextButton textButton;
    private TextureRegionDrawable background;
    private Image image;
    private Skin skin;
    private Stage stage;
    private LevelConnection levelConnection;

    public LevelsScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        table = new Table();
        form = new Table();
        scrollPaneTable = new Table();
        scrollPane = new ScrollPane(scrollPaneTable, skin);

        label = new Label("Level Select", skin.get("big-title", Label.LabelStyle.class));

        levelTables = new ArrayList<>();

        levelConnection = new LevelConnection();
        levelList = levelConnection.levelsList();

        createStructure();

        stage.addActor(table);
        stage.setScrollFocus(scrollPane);
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

        table.add(new NavigationBar().basicNavigationBar()).growX();

        table.row();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(background);
        scrollPaneTable.setBackground(background);

        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);

        for (int i = 0; i < levelList.size(); i++) {
            bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
            bgPixmap.setColor(Color.LIME);
            bgPixmap.fill();
            background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

            levelTables.add(createLevelProduct(i));
        }

        addProducts();

        form.add(label);
        form.row();
        form.add(scrollPane).growX().growY();
        table.add(form).growX().growY();
    }

    private void addProducts() {
        int index = 0;
        for (Table skin : levelTables) {
            if (index++ % 3 == 0) scrollPaneTable.row();
            scrollPaneTable.add(skin).pad(0, 10, 10, 10).width(300).height(200);
        }
    }

    private Table createLevelProduct(int index) {
        Table product = new Table();

        Level level = levelList.get(index);

        textButton = new TextButton(level.getCompleted() + " / Play", skin);
        image = new Image(new Texture(Constants.PLAYER_NORMAL));
        image.setAlign(Align.center);

        Label levelName = new Label(level.getName(), skin);
        levelName.setAlignment(Align.center);
        levelName.setColor(Color.RED);

        product.setBackground(background);

        product.add(levelName).pad(0, 10, 10, 10).growX().growY();
        product.row();
        product.add(image).pad(0, 10, 10, 10).width(100).height(100);
        product.row();
        product.add(textButton).height(50).growX();

        textButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Constants.CURRENT_LEVEL = index + 1;
                new ScreenChanger().changeScreen("GameScreen", level.getMap());
            }
        });
        return product;
    }
}