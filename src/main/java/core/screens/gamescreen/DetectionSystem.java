package core.screens.gamescreen;
import core.screens.gamescreen.objects.enemy.Enemy;
import core.screens.gamescreen.objects.player.Player;

public class DetectionSystem {

    public static Enemy detection(Enemy enemy, Player player) {
       return calculateDistance(enemy.getX(), enemy.getY(), player.getX(), player.getY(), 300) ? enemy : null;
    }

    private static boolean calculateDistance(float enemyX, float enemyY, float playerX, float playerY, float maxDistance) {
        return Math.sqrt(Math.pow(enemyX - playerX, 2) + Math.pow(enemyY - playerY, 2)) < maxDistance;
    }
}