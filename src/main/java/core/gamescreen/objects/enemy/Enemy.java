package core.gamescreen.objects.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Constants;
import core.gamescreen.helper.CollisionService;

public class Enemy extends EnemyEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite ENEMY_NORMAL, ENEMY_LEFT, ENEMY_RIGHT;

    private CollisionService rect;

    public Enemy(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
        ENEMY_NORMAL = new Sprite(new Texture(Constants.ENEMY_NORMAL));
        ENEMY_LEFT = new Sprite(new Texture(Constants.ENEMY_LEFT));
        ENEMY_RIGHT = new Sprite(new Texture(Constants.ENEMY_RIGHT));
        this.sprite = new Sprite(ENEMY_NORMAL);

        this.rect = new CollisionService(x, y, width, height);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;
        rect.move(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    public void destroyEnemy() {
        if (body.getFixtureList().first() != null) body.destroyFixture(body.getFixtureList().first());
    }

    public CollisionService getCollisionRect() {
        return this.rect;
    }
}