package core.screens.gamescreen;

import core.gamescreen.objects.bullet.Bullet;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.hostage.Hostage;
import core.gamescreen.objects.player.Player;


import static core.screens.gamescreen.GameScreen.*;

public class Collisions {

    public static void checkCollisions(Player player) {
        for (Enemy enemy : enemies) {
            for (Bullet bullet : playerBullets) {
                if (enemy.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                    bullet.destroyBullet();
                    enemy.destroyEnemy();
                    playerBulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    //navigationBar.updateEnemyKills();
                }
            }
        }
        playerBullets.removeAll(playerBulletsToRemove);
        enemies.removeAll(enemiesToRemove);

        for (Bullet bullet : enemyBullets) {
            if (player.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                bullet.destroyBullet();
                enemyBulletsToRemove.add(bullet);
            }
        }
        enemyBullets.removeAll(enemyBulletsToRemove);

        for (Hostage hostage : hostages) {
            for (Bullet bullet : playerBullets) {
                if (hostage.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                    bullet.destroyBullet();
                    hostage.destroyHostage();
                    playerBulletsToRemove.add(bullet);
                    hostagesToRemove.add(hostage);
                }
            }
        }
        playerBullets.removeAll(playerBulletsToRemove);
        hostages.removeAll(hostagesToRemove);

        for (Hostage hostage : hostages) {
            if (hostage.getCollisionRect().collidesWith(player.getCollisionRect())) {
                hostage.destroyHostage();
                hostagesToRemove.add(hostage);
            }
        }
        hostages.removeAll(hostagesToRemove);

        if (playerBullets.size() == 0 && playerBulletsToRemove.size() != 0) playerBulletsToRemove.clear();
        if (enemies.size() == 0 && enemiesToRemove.size() != 0) enemiesToRemove.clear();
        if (hostages.size() == 0 && hostagesToRemove.size() != 0) hostagesToRemove.clear();
    }
}