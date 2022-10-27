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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.Constants;
import core.screens.navigation.NavigationBar;

public class SettingsScreen extends ScreenAdapter {

    private Table table, scrollPaneTable, form;
    private ScrollPane scrollPane;
    private TextButton music, soundEffects, test2, test3, test4, test5, test6, test7, test8, test9;
    private Slider musicSlider ,soundEffectsSlider;
    private Skin skin;
    private Stage stage;

    public SettingsScreen() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        table = new Table();
        scrollPaneTable = new Table();
        scrollPane = new ScrollPane(scrollPaneTable, skin);
        form = new Table();
        music = new TextButton("Music", skin);
        soundEffects = new TextButton("Sound Effects", skin);
        test2 = new TextButton("Zvok2", skin);
        test3 = new TextButton("Zvok3", skin);
        test4 = new TextButton("Zvok4", skin);
        test5 = new TextButton("Zvok5", skin);
        test6 = new TextButton("Zvok6", skin);
        test7 = new TextButton("Zvok7", skin);
        test8 = new TextButton("Zvok8", skin);
        test9 = new TextButton("Save Settings", skin);
        musicSlider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        soundEffectsSlider = new Slider(0.0f, 100.0f, 1.0f, false, skin);

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

        music.setTouchable(Touchable.disabled);
        soundEffects.setTouchable(Touchable.disabled);

        scrollPaneTable.hasKeyboardFocus();
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);

        scrollPaneTable.add(music).padBottom(10).height(50).growX();
        scrollPaneTable.add(musicSlider).pad(10).height(10);
        scrollPaneTable.row();
        scrollPaneTable.add(soundEffects).padBottom(10).height(50).growX();
        scrollPaneTable.add(soundEffectsSlider).pad(10).height(10);
        scrollPaneTable.row();
        scrollPaneTable.add(test2).padBottom(10).height(50).growX();
        scrollPaneTable.row();
        scrollPaneTable.add(test3).padBottom(10).height(50).growX();
        scrollPaneTable.row();
        scrollPaneTable.add(test4).padBottom(10).height(50).growX();
        scrollPaneTable.row();
        scrollPaneTable.add(test5).padBottom(10).height(50).growX();
        scrollPaneTable.row();
        scrollPaneTable.add(test6).padBottom(10).height(50).growX();
        scrollPaneTable.row();
        scrollPaneTable.add(test7).padBottom(10).height(50).growX();
        scrollPaneTable.row();
        scrollPaneTable.add(test8).height(50).growX();

        Label label = new Label("Settings", skin);
        label.setColor(Color.BLACK);
        label.setFontScale(4f, 5f);

        form.add(label);
        form.row();
        form.add(scrollPane).padBottom(10).width(600).height(400);
        form.row();
        form.add(test9).width(400).height(50).growX();

        table.add(form).growX().growY();

        musicSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(musicSlider.getPercent());
            }
        });

        soundEffectsSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(soundEffectsSlider.getPercent());
            }
        });
    }
}