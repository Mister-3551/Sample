package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.Constants;
import core.screens.navigation.NavigationBar;

public class LevelsScreen extends ScreenAdapter {

    private Table table, scrollPaneTable, form;
    private ScrollPane scrollPane;
    private Skin skin;
    private Stage stage;

    public LevelsScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        table = new Table();
        form = new Table();
        scrollPaneTable = new Table();
        scrollPane = new ScrollPane(scrollPaneTable, skin);

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

        table.setFillParent(true);

        table.add(new NavigationBar().levelsAndSettingsNavigationBar()).growX();

        table.row();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        var background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(background);

        scrollPaneTable.hasKeyboardFocus();
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);

        for (int i = 1; i <= 10; i++) {
            TextButton textButton = new TextButton("Level " + i, skin);
            if (i != 1) textButton.setTouchable(Touchable.disabled);
            if (i != 10) scrollPaneTable.add(textButton).padBottom(10).height(50).growX();
            else scrollPaneTable.add(textButton).height(50).growX();
            scrollPaneTable.row();
        }

        Label label = new Label("Level Select", skin);
        label.setColor(Color.BLACK);
        label.setFontScale(4f, 5f);

        form.add(label);
        form.row();
        form.add(scrollPane).width(600).height(400);

        table.add(form).growX().growY();
    }
}
