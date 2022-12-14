package core.screens.gamescreen.objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class PlayerEntity {

    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;

    public PlayerEntity(float width, float height, Body body) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update();
    public Body getBody() {
        return body;
    }
    public float getX() { return x;}
    public float getY() { return y;}
}