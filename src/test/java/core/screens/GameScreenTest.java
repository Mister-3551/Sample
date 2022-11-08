package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import core.gamescreen.helper.BodyHelperService;
import core.gamescreen.objects.player.Player;
import org.junit.Test;

import static core.Constants.SCREENHEIGHT;
import static core.Constants.SCREENWIDTH;

public class GameScreenTest {

    @Test
    public void Angle() {
        //Body body = BodyHelperService.createObjectBody(14 * 1.5f, 34 * 1.5f, 100, 60, null);
        //Player player = new Player(14 * 1.5f, 34 * 1.5f, body);

        var gameWidth = 1200;
        var gameHeight = 800;

        var mouseX = 800;
        var mouseY = 1200;

        var deltaY = gameHeight - mouseY;
        var deltaX = gameWidth - mouseX;

        var rad = Math.atan2(deltaY, deltaX);

        var deg = Math.round(rad * (180 / Math.PI));

        System.out.println(rad + " " + deg);
    }
}