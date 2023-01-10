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

        for (Body body : WorldCollision.enemyShootPlayer) {
            if (player.getBody() == body) {
                WorldCollision.enemyShootPlayerToRemove.add(body);
            }
        }
        WorldCollision.enemyShootPlayer.removeAll(WorldCollision.enemyShootPlayerToRemove);


        for (Body body : WorldCollision.playerShootHostage) {
            for (Hostage hostage : hostages) {
                if (hostage.getBody() == body) {
                    hostage.destroyHostage();
                    hostagesToRemove.add(hostage);
                    WorldCollision.playerBulletsHostageToRemove.add(body);
                }
            }
        }
        WorldCollision.playerShootHostage.removeAll(WorldCollision.playerBulletsHostageToRemove);
        hostages.removeAll(hostagesToRemove);

        for (Body body : WorldCollision.enemyShootHostage) {
            for (Hostage hostage : hostages) {
                if (hostage.getBody() == body) {
                    hostage.destroyHostage();
                    hostagesToRemove.add(hostage);
                    WorldCollision.enemyBulletsHostageToRemove.add(body);
                }
            }
        }
        WorldCollision.enemyShootHostage.removeAll(WorldCollision.enemyBulletsHostageToRemove);
        hostages.removeAll(hostagesToRemove);

        for (Body body : WorldCollision.enemyShootEnemy) {
            for (Enemy enemy : enemies) {
                if (enemy.getBody() == body) {
                    enemy.destroyEnemy();
                    navigationBar.updateEnemyKills();
                    enemiesToRemove.add(enemy);
                    WorldCollision.enemyWithBulletToRemove.add(body);
                }
            }
        }
        WorldCollision.enemyShootEnemy.removeAll(WorldCollision.enemyWithBulletToRemove);
        enemies.removeAll(enemiesToRemove);

        for (Body body : WorldCollision.playerShootEnemy) {
            for (Enemy enemy : enemies) {
                if (enemy.getBody() == body) {
                    enemy.destroyEnemy();
                    navigationBar.updateEnemyKills();
                    enemiesToRemove.add(enemy);
                    WorldCollision.bulletsEnemiesToRemove.add(body);
                }
            }
        }
        WorldCollision.playerShootEnemy.removeAll(WorldCollision.bulletsEnemiesToRemove);
        enemies.removeAll(enemiesToRemove);

        for (Body body : WorldCollision.playerCollectHostage) {
            for (Hostage hostage : hostages) {
                if (hostage.getBody() == body) {
                    hostage.destroyHostage();
                    navigationBar.updateHostageSaved();
                    hostagesToRemove.add(hostage);
                    WorldCollision.playerHostagesToRemove.add(body);
                }
            }
        }
        WorldCollision.playerCollectHostage.removeAll(WorldCollision.playerHostagesToRemove);
        hostages.removeAll(hostagesToRemove);

        for (Body body : WorldCollision.playerBullets) {
            for (Bullet bullet : playerBullets) {
                if (bullet.getBody() == body) {
                    bullet.destroyBullet();
                    playerBulletsToRemove.add(bullet);
                    WorldCollision.playerBulletsToRemove.add(body);
                }
            }
        }
        WorldCollision.playerBullets.removeAll(WorldCollision.playerBulletsToRemove);
        playerBullets.removeAll(playerBulletsToRemove);

        for (Body body : WorldCollision.enemyBullets) {
            for (Bullet bullet : enemyBullets) {
                if (bullet.getBody() == body) {
                    bullet.destroyBullet();
                    enemyBulletsToRemove.add(bullet);
                    WorldCollision.enemyBulletsToRemove.add(body);
                }
            }
        }
        WorldCollision.enemyBullets.removeAll(WorldCollision.enemyBulletsToRemove);
        enemyBullets.removeAll(enemyBulletsToRemove);



        for (Body body : WorldCollision.enemyBulletTouchPlayerBullet) {
            for (Bullet bullet : enemyBullets) {
                if (bullet.getBody() == body) {
                    bullet.destroyBullet();
                    enemyBulletsToRemove.add(bullet);
                    WorldCollision.enemyBulletsToRemove.add(body);
                }
            }
        }

        for (Body body : WorldCollision.enemyBulletTouchPlayerBullet) {
            for (Bullet bullet : playerBullets) {
                if (bullet.getBody() == body) {
                    bullet.destroyBullet();
                    playerBulletsToRemove.add(bullet);
                    WorldCollision.playerBulletsToRemove.add(body);
                }
            }
        }

        if (playerBullets.size() == 0 && playerBulletsToRemove.size() != 0) playerBulletsToRemove.clear();
        if (enemies.size() == 0 && enemiesToRemove.size() != 0) enemiesToRemove.clear();
        if (hostages.size() == 0 && hostagesToRemove.size() != 0) hostagesToRemove.clear();
    }
}