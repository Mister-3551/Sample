package core.screens.gamescreen;

import com.badlogic.gdx.physics.box2d.*;
import core.screens.gamescreen.objects.bullet.Bullet;

import java.util.ArrayList;

public class WorldCollision {

    public static ArrayList<Body> fallenBullets = new ArrayList<>();

    public static void checkCollision(World world) {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {

                Fixture fa = contact.getFixtureA();
                Fixture fb = contact.getFixtureB();

                if (fa.getBody().getUserData() == "bullet") fallenBullets.add(fa.getBody());
                else if (fb.getBody().getUserData() == "bullet") fallenBullets.add(fb.getBody());
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
}