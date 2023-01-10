package core.screens.gamescreen;

import com.badlogic.gdx.physics.box2d.Body;
import core.screens.gamescreen.objects.bullet.Bullet;
import core.screens.gamescreen.objects.enemy.Enemy;
import core.screens.gamescreen.objects.hostage.Hostage;
import core.screens.gamescreen.objects.player.Player;
import core.screens.navigation.NavigationBar;

import static core.screens.gamescreen.GameScreen.*;

public class Collisions {

    public static void checkCollisions(Player player, NavigationBar navigationBar) {

        /*for (Enemy enemy : enemies) {
            for (Bullet bullet : playerBullets) {
                if (enemy.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                    bullet.destroyBullet();
                    enemy.destroyEnemy();
                    playerBulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    navigationBar.updateEnemyKills();
                }
            }
        }
        playerBullets.removeAll(playerBulletsToRemove);
        enemies.removeAll(enemiesToRemove);*/

        for (Bullet bullet : enemyBullets) {
            if (player.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                bullet.destroyBullet();
                enemyBulletsToRemove.add(bullet);
            }
        }
        enemyBullets.removeAll(enemyBulletsToRemove);

        /*for (Hostage hostage : hostages) {
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
        hostages.removeAll(hostagesToRemove);*/


        for (Body body : WorldCollision.bulletsEnemies) {
            for (Enemy enemy : enemies) {
                if (enemy.getBody() == body) {
                    enemy.destroyEnemy();
                    enemiesToRemove.add(enemy);
                    WorldCollision.bulletsEnemiesToRemove.add(body);
                }
            }
        }
        WorldCollision.bulletsEnemies.removeAll(WorldCollision.bulletsEnemiesToRemove);
        enemies.removeAll(enemiesToRemove);

        for (Body body : WorldCollision.playerHostages) {
            for (Hostage hostage : hostages) {
                if (hostage.getBody() == body) {
                    hostage.destroyHostage();
                    hostagesToRemove.add(hostage);
                    WorldCollision.playerHostagesToRemove.add(body);
                }
            }
        }
        WorldCollision.playerHostages.removeAll(WorldCollision.playerHostagesToRemove);
        hostages.removeAll(hostagesToRemove);

        for (Body body : WorldCollision.fallenPlayerBullets) {
            for (Bullet bullet : playerBullets) {
                if (bullet.getBody() == body) {
                    bullet.destroyBullet();
                    playerBulletsToRemove.add(bullet);
                    WorldCollision.fallenPlayerBulletsToRemove.add(body);
                }
            }
        }
        WorldCollision.fallenPlayerBullets.removeAll(WorldCollision.fallenPlayerBulletsToRemove);
        playerBullets.removeAll(playerBulletsToRemove);

        for (Body body : WorldCollision.fallenEnemyBullets) {
            for (Bullet bullet : enemyBullets) {
                if (bullet.getBody() == body) {
                    bullet.destroyBullet();
                    enemyBulletsToRemove.add(bullet);
                    WorldCollision.fallenEnemyBulletsToRemove.add(body);
                }
            }
        }
        WorldCollision.fallenEnemyBullets.removeAll(WorldCollision.fallenPlayerBulletsToRemove);
        enemyBullets.removeAll(enemyBulletsToRemove);

        if (playerBullets.size() == 0 && playerBulletsToRemove.size() != 0) playerBulletsToRemove.clear();
        if (enemies.size() == 0 && enemiesToRemove.size() != 0) enemiesToRemove.clear();
        if (hostages.size() == 0 && hostagesToRemove.size() != 0) hostagesToRemove.clear();
    }
}