package core.gamescreen.objects.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ContactListener;
import core.Constants;
import core.gamescreen.helper.BodyHelperService;
import core.gamescreen.helper.CollisionService;
import core.screens.GameScreen;

import java.util.ArrayList;

public class Bullet extends BulletEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite BULLET_NORMAL;
    public boolean remove;

    public Bullet(float width, float height, Body body, int direction) {
        super(width, height, body, direction);
        this.speed = 10f;
        this.velX = direction;
        this.jumpCounter = 0;
        BULLET_NORMAL = new Sprite(new Texture(Gdx.files.internal("gameScreen/bullet/bullet.png")));
        this.sprite = new Sprite(BULLET_NORMAL);

        remove = false;
    }

    private float pos = 0.0f;

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
        body.setLinearVelocity(velX * speed, -0.0005f);

        if (x < 65 || x > 5906 || y < 64 || pos == x) {
            remove = true;
            body.destroyFixture(body.getFixtureList().first());
        }
        pos = x;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }
}