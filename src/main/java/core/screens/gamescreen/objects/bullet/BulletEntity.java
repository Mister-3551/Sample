package core.screens.gamescreen.objects.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BulletEntity {

    protected float x, y, width, height, angle, speed;
    protected Body body;

    public BulletEntity(float width, float height, Body body, float angle) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.angle = angle;
        this.speed = 0;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update();
    public Body getBody() {
        return body;
    }
}