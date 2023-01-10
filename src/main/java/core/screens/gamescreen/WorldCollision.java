package core.screens.gamescreen;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class WorldCollision {

    public static ArrayList<Body> fallenPlayerBullets = new ArrayList<>();
    public static ArrayList<Body> fallenEnemyBullets = new ArrayList<>();
    public static ArrayList<Body> fallenPlayerBulletsToRemove = new ArrayList<>();
    public static ArrayList<Body> fallenEnemyBulletsToRemove = new ArrayList<>();

    public static ArrayList<Body> playerHostages = new ArrayList<>();
    public static ArrayList<Body> bulletsEnemies = new ArrayList<>();
    public static ArrayList<Body> playerHostagesToRemove = new ArrayList<>();
    public static ArrayList<Body> bulletsEnemiesToRemove = new ArrayList<>();

    public static void checkCollision(World world) {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {

                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();

                bulletsCollision(fa, fb);
                playerHostageCollision(fa, fb);
                bulletEnemyCollision(fa, fb);
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

    private static void bulletsCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "PlayerBullet" && fb.getBody().getType() == BodyDef.BodyType.StaticBody) fallenPlayerBullets.add(fa.getBody());
        else if (fb.getBody().getUserData() == "PlayerBullet" && fa.getBody().getType() == BodyDef.BodyType.StaticBody) fallenPlayerBullets.add(fb.getBody());
        else if (fa.getBody().getUserData() == "EnemyBullet" && fb.getBody().getType() == BodyDef.BodyType.StaticBody) fallenEnemyBullets.add(fa.getBody());
        else if (fb.getBody().getUserData() == "EnemyBullet" && fa.getBody().getType() == BodyDef.BodyType.StaticBody) fallenEnemyBullets.add(fb.getBody());
    }

    private static void playerHostageCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "Player" && fb.getBody().getUserData() == "Hostage") playerHostages.add(fb.getBody());
        else if (fa.getBody().getUserData() == "Hostage" && fb.getBody().getUserData() == "Player") playerHostages.add(fa.getBody());
    }

    private static void bulletEnemyCollision(Fixture fa, Fixture fb) {
        if (fa.getBody().getUserData() == "PlayerBullet" && fb.getBody().getUserData() == "Enemy") bulletsEnemies.add(fb.getBody());
        else if (fa.getBody().getUserData() == "Enemy" && fb.getBody().getUserData() == "PlayerBullet") bulletsEnemies.add(fa.getBody());
    }
}