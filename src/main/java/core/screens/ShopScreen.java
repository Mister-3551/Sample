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
import core.screens.navigation.NavigationBar;

import java.util.ArrayList;

public class ShopScreen extends ScreenAdapter {

    private Table table, scrollPaneTable, form;

    private ArrayList<Table> skinTables, mapTables;
    private ScrollPane scrollPane;
    private Label label;
    private TextButton textButton;
    private TextureRegionDrawable background;
    private Image image;
    private Skin skin;
    private Stage stage;

    public ShopScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        table = new Table();
        form = new Table();
        scrollPaneTable = new Table();
        scrollPane = new ScrollPane(scrollPaneTable, skin);

        label = new Label("Shop", skin.get("big-title", Label.LabelStyle.class));

        skinTables = new ArrayList<>();
        mapTables = new ArrayList<>();

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

        for (int i = 0; i < 10; i++) {
            bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
            bgPixmap.setColor(Color.LIME);
            bgPixmap.fill();
            background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

            skinTables.add(createSkinProduct());
            mapTables.add(createMapProduct());
        }

        addProducts();

        form.add(label);
        form.row();
        form.add(scrollPane).growX().growY();
        table.add(form).growX().growY();
    }

    private void addProducts() {
        Label skins = new Label("Skins", skin);
        skins.setAlignment(Align.center);
        Label maps = new Label("Maps", skin);
        maps.setAlignment(Align.center);

        for (Table skin : skinTables) {
            scrollPaneTable.add(skin).pad(0, 10, 10, 10).width(300).height(200);
        }
        scrollPaneTable.row();
        for (Table map : mapTables) {
            scrollPaneTable.add(map).pad(0, 10, 0, 10).width(300).height(200);
        }
    }

    private Table createSkinProduct() {
        Table product = new Table();

        textButton = new TextButton("Buy", skin);
        image = new Image(new Texture(Constants.PLAYER_NORMAL));
        image.setAlign(Align.center);

        Label label = new Label("Skin", skin);
        label.setAlignment(Align.center);
        label.setColor(Color.RED);

        product.setBackground(background);

        product.add(label).pad(0, 10, 10, 10).growX().growY();
        product.row();
        product.add(image).pad(0, 10, 10, 10).width(100).height(100);
        product.row();
        product.add(textButton).height(50).growX();

        textButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        return product;
    }

    private Table createMapProduct() {
        Table product = new Table();

        textButton = new TextButton("Buy", skin);
        image = new Image(new Texture(Constants.ENEMY_NORMAL));
        image.setAlign(Align.center);

        Label label = new Label("Map", skin);
        label.setAlignment(Align.center);
        label.setColor(Color.RED);

        product.setBackground(background);

        product.add(label).pad(0, 10, 10, 10).growX().growY();
        product.row();
        product.add(image).pad(0, 10, 10, 10).width(100).height(100);
        product.row();
        product.add(textButton).height(50).growX();

        textButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        return product;
    }
}