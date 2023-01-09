package core.gamescreen.objects.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.GameData;
import core.gamescreen.helper.CollisionService;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.player.Player;

public class Bullet extends BulletEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite BULLET_NORMAL;
    public boolean remove;
    private float posX, posY = 0.0f;
    private final CollisionService rect;

    public Bullet(float width, float height, Body body, float angle) {
        super(width, height, body, angle);
        this.speed = 20f;
        this.jumpCounter = 0;
        this.BULLET_NORMAL = new Sprite(new Texture(Gdx.files.internal(GameData.Skins.Bullet.BULLET)));
        this.sprite = new Sprite(BULLET_NORMAL);
        this.remove = false;
        this.rect = new CollisionService(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    @Override
    public void update() {
        x = body.getPosition().x * GameData.PPM;
        y = body.getPosition().y * GameData.PPM;

        body.setLinearVelocity((float) (speed * Math.cos(angle)), (float) (speed * Math.sin(angle)));

        this.rect.move(x, y);
    }

    public void destroyBullet() {
        if (body.getFixtureList().size > 0) body.destroyFixture(body.getFixtureList().first());
    }

    public CollisionService getCollisionRect() {
        return rect;
    }

    public static float getBulletAngle(Player player, OrthographicCamera camera) {
        return (float) Math.atan2(diffY(camera, player), diffX(camera, player));
    }

    public static float diffX(OrthographicCamera camera, Player player) {
        int xMouse = Gdx.input.getX();
        var XMouse = xMouse + camera.position.x - GameData.SCREEN_WIDTH / 2;
        return XMouse - player.getX();
    }

    public static float diffY(OrthographicCamera camera, Player player) {
        // mouse ordinate direction is the opposite to pane window
        int yMouse = GameData.SCREEN_HEIGHT - Gdx.input.getY();
        // translate screen coordinates to current camera position in world coordinates
        var YMouse = yMouse + camera.position.y - GameData.SCREEN_HEIGHT / 2;
        return YMouse - player.getY();
    }

    public static float getBulletAngleEnemy(Enemy enemy, Player player) {
        return (float) Math.atan2(diffYEnemy(player, enemy), diffXEnemy(player, enemy));
    }

    public static float diffXEnemy(Player player, Enemy enemy) {
        return player.getX() - enemy.getX();
    }

    public static float diffYEnemy(Player player, Enemy enemy) {
        return player.getY() - enemy.getY();
    }
}