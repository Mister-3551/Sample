package core.screens.skinscreen;

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
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.GameData;
import core.ResponseDataConnection;
import core.downloadfile.DownloadFile;
import core.screens.ScreenChanger;
import core.screens.levelsscreen.Level;
import core.screens.navigation.NavigationBar;

import java.util.ArrayList;

public class SkinScreen extends ScreenAdapter {

    private Table stageTable;
    public static Image image;
    private Skin skin;
    private Stage stage;
    private ArrayList<core.objects.Skin> skinsList;

    private String skinsDirectory;

    public SkinScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));
        stageTable = new Table();

        skinsList = new ArrayList<>();
        try {
            skinsList = ResponseDataConnection.Skins.getSkins();
            for (core.objects.Skin playerSkin : skinsList) skinsDirectory = DownloadFile.getOwnedSkins(playerSkin.getPicture().split("-")[0], playerSkin.getPicture());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        stage.setViewport(new FitViewport(width, height));
        stage.getViewport().update(width, height, true);
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

        Label title = new Label("Owned Skins", skin.get("big-title", Label.LabelStyle.class));

        product(scrollPaneTable);

        image = null;

        stageTable.setFillParent(true);

        stageTable.add(new NavigationBar().basicNavigationBar()).pad(0, 0, 100, 0).growX();
        stageTable.row();
        stageTable.add(title);

        stageTable.row();
        stageTable.add(scrollPane).growX().maxWidth(GameData.LevelScreen.SCROLL_PANE_SIZE);
        stageTable.row();
        stageTable.add(new Table()).growY();
    }

    private void product(Table scrollPaneTable) {
        Table product;
        int index = 0;
        for (core.objects.Skin skin : skinsList) {

            Image imageNotFound = null;
            try {
                image = new Image(new Texture(skinsDirectory + skin.getPicture() + ".png"));
                image.setAlign(Align.center);
            } catch (Exception e) {
                imageNotFound = new Image(new Texture(new Pixmap(1, 1, Pixmap.Format.RGB565)));
                image = imageNotFound;
            }

            product = new Table();

            TextButton use = new TextButton("", this.skin);
            var current = GameData.Player.CURRENT_SKIN;
            if (current.matches(skin.getPicture().split("-")[1])) use.setText("In use");
            else use.setText("Use");
            if (imageNotFound != null) {
                use.setText("Error");
                use.setDisabled(true);
            }

            Label skinName = new Label(skin.getName(), this.skin);
            skinName.setAlignment(Align.center);
            skinName.setColor(Color.RED);

            product.setBackground(setBackground(Color.GREEN));

            product.add(skinName).pad(10, 10, 10, 10).growX();
            product.row();
            product.add(image).pad(0, 10, 10, 10).width(100).height(100);
            product.row();
            product.add(use).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 4 == 0) scrollPaneTable.row();
            scrollPaneTable.add(product).pad(0, 10, 10, 10).width(250).growY();

            use.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    GameData.Player.CURRENT_SKIN = skin.getPicture().split("-")[1];
                    scrollPaneTable.clear();
                    product(scrollPaneTable);
                }
            });
        }
        if (skinsList.size() == 1) for (int i = 0; i < 3; i++) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY();
        if (skinsList.size() == 2) for (int i = 0; i < 2; i++) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY();
        if (skinsList.size() == 3) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY();
    }

    private TextureRegionDrawable setBackground(Color color) {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(color);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }
}