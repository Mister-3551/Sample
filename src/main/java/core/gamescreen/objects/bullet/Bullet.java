package core.gamescreen.objects.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import core.Constants;
import core.gamescreen.helper.CollisionService;
import core.gamescreen.objects.player.Player;

import static core.Constants.*;

public class Bullet extends BulletEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite BULLET_NORMAL;
    public boolean remove;
    private float posX, posY = 0.0f;

    private CollisionService rect;

    public Bullet(float width, float height, Body body, float directionX, float directionY) {
        super(width, height, body, directionX, directionY);
        this.speed = 10f;
        this.jumpCounter = 0;
        this.BULLET_NORMAL = new Sprite(new Texture(Gdx.files.internal(Constants.BULLET)));
        this.sprite = new Sprite(BULLET_NORMAL);
        this.remove = false;
        this.rect = new CollisionService(x, y, width, height);
    }
    @Override
    public void update() {
        x = body.getPosition().x * Constants.PPM;
        y = body.getPosition().y * Constants.PPM;

        body.setLinearVelocity(speed * MathUtils.cos(directionX), speed * MathUtils.sin(directionY));

        if (posX == x || posY == y) {
            remove = true;
            body.destroyFixture(body.getFixtureList().first());
        }
        posX = x;
        posY = y;

        this.rect.move(x, y);
    }
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    public void destroyBullet() {
        if (body.getFixtureList().first() != null) body.destroyFixture(body.getFixtureList().first());
    }

    public CollisionService getCollisionRect() {
        return rect;
    }

    public static float getBulletDirection(Player player) {
        var deltaY = SCREENHEIGHT - Gdx.input.getY() - player.getY();
        //var deltaX = (player.getX() - Gdx.input.getX()) * -1;
        var deltaX = (SCREENWIDTH / 2 - Gdx.input.getX());
        var rad = Math.atan2(deltaY, deltaX);
        var deg = Math.round(rad * (180 / Math.PI));

        var radians = ((float) (deg * Math.PI / 180));
        return radians;
    }
}