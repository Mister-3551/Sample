package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import helper.Constants;

import static helper.Constants.*;

public class Player extends PlayerEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite PLAYER_NORMAL, PLAYER_LEFT, PLAYER_LEFT_SWORD, PLAYER_RIGHT, PLAYER_RIGHT_SWORD;

    private boolean flip = false;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
        PLAYER_NORMAL = new Sprite(new Texture(Constants.PLAYER_NORMAL));
        PLAYER_LEFT = new Sprite(new Texture(Constants.PLAYER_LEFT));
        PLAYER_LEFT_SWORD = new Sprite(new Texture(Constants.PLAYER_LEFT_SWORD));
        PLAYER_RIGHT = new Sprite(new Texture(Constants.PLAYER_RIGHT));
        PLAYER_RIGHT_SWORD = new Sprite(new Texture(Constants.PLAYER_RIGHT_SWORD));
        this.sprite = new Sprite(PLAYER_NORMAL);
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        checkUserInput();
        setPlayerBounds(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    private void checkUserInput() {
        velX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (!this.sprite.equals(PLAYER_LEFT)) setSprite(PLAYER_LEFT_SWORD);
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (!this.sprite.equals(PLAYER_RIGHT)) setSprite(PLAYER_RIGHT);
            velX = 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && jumpCounter < 2) {
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

    public Rectangle getPlayerBounds() {
        return sprite.getBoundingRectangle();
    }

    private void setPlayerBounds(float x, float y) {
        sprite.setBounds(x , y, width, height);
    }
}