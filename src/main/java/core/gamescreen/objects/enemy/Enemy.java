package core.gamescreen.objects.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.Constants;

public class Enemy extends EnemyEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite ENEMY_NORMAL, ENEMY_LEFT, ENEMY_RIGHT;

    public Enemy(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
        ENEMY_NORMAL = new Sprite(new Texture(Constants.ENEMY_NORMAL));
        ENEMY_LEFT = new Sprite(new Texture(Constants.ENEMY_LEFT));
        ENEMY_RIGHT = new Sprite(new Texture(Constants.ENEMY_RIGHT));
        this.sprite = new Sprite(ENEMY_NORMAL);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
        checkUserInput();
        setEnemyBounds(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    private void checkUserInput() {
        velX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!this.sprite.equals(ENEMY_LEFT)) setSprite(ENEMY_LEFT);
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!this.sprite.equals(ENEMY_RIGHT)) setSprite(ENEMY_RIGHT);
            velX = 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCounter < 2) {
            float force = body.getMass() * 18;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }
        if (body.getLinearVelocity().y == 0) jumpCounter = 0;
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Rectangle getEnemyBounds() {
        return sprite.getBoundingRectangle();
    }

    private void setEnemyBounds(float x, float y) {
        sprite.setBounds(x, y, width, height);
    }
}