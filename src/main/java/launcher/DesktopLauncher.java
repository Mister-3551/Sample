package launcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.Boot;
import core.GameData;

import java.awt.*;

public class DesktopLauncher {

    private static Lwjgl3ApplicationConfiguration configuration;

    public static void main(String[] args) {
        configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(144);
        configuration.setForegroundFPS(144);
        configuration.useVsync(true);
        configuration.setMaximized(true);
        configuration.setResizable(true);
        configuration.setTitle("Game Name");
        configuration.setWindowIcon(GameData.Skins.Player.PLAYER_LEFT_SWORD);
        new Lwjgl3Application(new Boot(), configuration);
    }
}