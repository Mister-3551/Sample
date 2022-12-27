package core.gamescreen;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.player.Player;

public class DetectionSystem {

    public static Enemy detection(Enemy enemy, Player player) {
       return calculateDistance(enemy.getX(), enemy.getY(), player.getX(), player.getY(), 300) ? enemy : null;
    }

    private static boolean calculateDistance(float enemyX, float enemyY, float playerX, float playerY, float maxDistance) {
        float distanceX = enemyX > playerX ? enemyX - playerX : playerX - enemyX;
        float distanceY = enemyY > playerY ? enemyY - playerY : playerY - enemyY;

        return distanceX <= maxDistance && distanceY <= maxDistance;
    }
}