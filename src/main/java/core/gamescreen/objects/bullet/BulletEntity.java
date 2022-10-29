package core.gamescreen.objects.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BulletEntity {

    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;

    public BulletEntity(float width, float height, Body body, int direction) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = direction;
        this.velY = 0;
        this.speed = 0;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);

    public Body getBody() {
        return body;
    }
}