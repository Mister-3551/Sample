package core.screens.gamescreen;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class WorldCollision {

    public static ArrayList<Body> playerBullets = new ArrayList<>();
    public static ArrayList<Body> enemyBullets = new ArrayList<>();
    public static ArrayList<Body> playerBulletsToRemove = new ArrayList<>();
    public static ArrayList<Body> enemyBulletsToRemove = new ArrayList<>();
    public static ArrayList<Body> playerCollectHostage = new ArrayList<>();
    public static ArrayList<Body> playerShootEnemy = new ArrayList<>();
    public static ArrayList<Body> playerHostagesToRemove = new ArrayList<>();
    public static ArrayList<Body> bulletsEnemiesToRemove = new ArrayList<>();
    public static ArrayList<Body> playerShootHostage = new ArrayList<>();
    public static ArrayList<Body> playerBulletsHostageToRemove = new ArrayList<>();
    public static ArrayList<Body> enemyShootHostage = new ArrayList<>();
    public static ArrayList<Body> enemyBulletsHostageToRemove = new ArrayList<>();
    public static ArrayList<Body> enemyShootEnemy = new ArrayList<>();
    public static ArrayList<Body> enemyWithBulletToRemove = new ArrayList<>();
    public static ArrayList<Body> enemyShootPlayer = new ArrayList<>();
    public static ArrayList<Body> enemyShootPlayerToRemove = new ArrayList<>();
    public static ArrayList<Body> enemyBulletTouchPlayerBullet = new ArrayList<>();
    public static ArrayList<Body> enemyBulletTouchPlayerBulletToRemove = new ArrayList<>();

    public static void checkCollision(World world) {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {

                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();

                bulletsCollision(fa, fb);
                playerCollectHostageCollision(fa, fb);
                playerShootEnemyCollision(fa, fb);
                playerOrEnemyShootHostageCollision(fa, fb);
                enemyShootEnemyCollision(fa, fb);
                enemyShootPlayerCollision(fa, fb);
                enemyBulletTouchPlayerBullet(fa, fb);
                enemyBulletTouchEnemyBullet(fa, fb);
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold manifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
        });
    }

    private static void bulletsCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "PlayerBullet" && fb.getBody().getType() == BodyDef.BodyType.StaticBody) playerBullets.add(fa.getBody());
        else if (fb.getBody().getUserData() == "PlayerBullet" && fa.getBody().getType() == BodyDef.BodyType.StaticBody) playerBullets.add(fb.getBody());

        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getType() == BodyDef.BodyType.StaticBody) enemyBullets.add(fa.getBody());
        else if (fb.getBody().getUserData() == "EnemyBullet" && fa.getBody().getType() == BodyDef.BodyType.StaticBody) enemyBullets.add(fb.getBody());
    }

    private static void playerCollectHostageCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Hostage") playerCollectHostage.add(fb.getBody());
        else if (fa.getBody().getUserData() == "Hostage" && fb.getBody().getUserData() == "Player") playerCollectHostage.add(fa.getBody());
    }

    private static void playerShootEnemyCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "PlayerBullet" && fb.getBody().getUserData() == "Enemy") {
            playerShootEnemy.add(fb.getBody());
            playerBullets.add(fa.getBody());
        } else if (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "PlayerBullet") {
            playerShootEnemy.add(fa.getBody());
            playerBullets.add(fb.getBody());
        }
    }

    private static void playerOrEnemyShootHostageCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "PlayerBullet" && fb.getBody().getUserData() == "Hostage") {
            playerShootHostage.add(fb.getBody());
            playerBullets.add(fa.getBody());
        } else if (fa.getBody().getUserData() == "Hostage" && fb.getBody().getUserData() == "PlayerBullet") {
            playerShootHostage.add(fa.getBody());
            playerBullets.add(fb.getBody());
        }

        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "Hostage") {
            enemyShootHostage.add(fb.getBody());
            enemyBullets.add(fa.getBody());
        }
        else if (fa.getBody().getUserData() == "Hostage" && fb.getBody().getUserData() == "EnemyBullet") {
            enemyShootHostage.add(fa.getBody());
            enemyBullets.add(fb.getBody());
        }
    }

    private static void enemyShootEnemyCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "Enemy") enemyShootEnemy.add(fb.getBody());
        else if (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "EnemyBullet") enemyShootEnemy.add(fa.getBody());

        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "Enemy") enemyBullets.add(fa.getBody());
        else if (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "EnemyBullet") enemyBullets.add(fb.getBody());
    }

    private static void enemyShootPlayerCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "Player") enemyShootPlayer.add(fb.getBody());
        else if (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "EnemyBullet") enemyShootPlayer.add(fa.getBody());

        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "Player") enemyBullets.add(fa.getBody());
        else if (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "EnemyBullet") enemyBullets.add(fb.getBody());
    }

    private static void enemyBulletTouchPlayerBullet(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "PlayerBullet") {
            enemyBulletTouchPlayerBullet.add(fa.getBody());
            enemyBulletTouchPlayerBullet.add(fb.getBody());
        } else if (fa.getBody().getUserData() == "PlayerBullet" && fb.getBody().getUserData() == "EnemyBullet") {
            enemyBulletTouchPlayerBullet.add(fa.getBody());
            enemyBulletTouchPlayerBullet.add(fb.getBody());
        }
    }

    private static void enemyBulletTouchEnemyBullet(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "EnemyBullet") {
            enemyBulletTouchPlayerBullet.add(fa.getBody());
            enemyBulletTouchPlayerBullet.add(fb.getBody());
        } else if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getUserData() == "EnemyBullet") {
            enemyBulletTouchPlayerBullet.add(fa.getBody());
            enemyBulletTouchPlayerBullet.add(fb.getBody());
        }
    }
}