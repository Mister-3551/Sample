package launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.Boot;

public class DesktopLauncher {

    private static Lwjgl3ApplicationConfiguration configuration;

    public static void main(String[] args) {
        configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(60);
        configuration.useVsync(true);
        configuration.setTitle("Sample Case");

        configuration.setWindowedMode(1000, 800);
        //configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        new Lwjgl3Application(new Boot(), configuration);
    }
}
