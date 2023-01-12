package core.screens.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import core.GameData;

public class PauseMenu {

    public static Table create() {

        Table table = new Table();

        var skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));

        Label title = new Label("Pause", skin.get("big-title", Label.LabelStyle.class));
        title.setAlignment(Align.center);
        title.setColor(Color.RED);

        Table lower = new Table();
        Table panel = new Table();
        Table form = new Table();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();
        var background = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        panel.setBackground(background);
        bgPixmap.dispose();

        form.add(title).growX().growY();
        form.row();
        form.add(new TextButton("Continue", skin)).pad(0, 10, 10, 10).height(50).growX();
        form.row();
        form.add(new TextButton("Restart", skin)).pad(0, 10, 10, 10).height(50).growX();
        form.row();
        form.add(new TextButton("Exit", skin)).pad(0, 10, 10, 10).height(50).growX();

        panel.add(form).growX();

        lower.add(panel).width(400).height(300);
        table.add(lower).growX().growY();

        table.setPosition(GameData.SCREEN_WIDTH / 2f, GameData.SCREEN_HEIGHT / 2f);

        return table;
    }
}