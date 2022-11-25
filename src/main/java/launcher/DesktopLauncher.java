package launcher;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.Boot;
import core.Constants;
import core.GameData;

public class DesktopLauncher {

    private static Lwjgl3ApplicationConfiguration configuration;

    public static void main(String[] args) {
        configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(60);
        configuration.useVsync(true);
        configuration.setResizable(false);
        configuration.setTitle("Sample Case");
        configuration.setWindowIcon(Constants.PLAYER_LEFT);
        configuration.setWindowedMode(GameData.SCREENWIDTH, GameData.SCREENHEIGHT);
        //configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        new Lwjgl3Application(new Boot(), configuration);
    }
}