package core.gamescreen.objects.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BulletEntity {

    protected float x, y, width, height, directionX, directionY, speed;
    protected Body body;

    public BulletEntity(float width, float height, Body body, float directionX, float directionY) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.directionX = directionX;
        this.directionY = directionY;
        this.speed = 0;
    }

    public abstract void update();
    public abstract void render(SpriteBatch batch);

    public Body getBody() {
        return body;
    }
}