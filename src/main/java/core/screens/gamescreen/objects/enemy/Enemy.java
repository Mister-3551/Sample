package core.screens.gamescreen.objects.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import core.GameData;
import core.screens.gamescreen.helper.BodyHelperService;
import core.screens.gamescreen.helper.CollisionObject;
import core.screens.gamescreen.objects.bullet.Bullet;
import core.screens.gamescreen.objects.player.Player;
import core.screens.gamescreen.GameScreen;

import java.util.ArrayList;

public class Enemy extends EnemyEntity {

    private Sprite sprite;
    private final Sprite ENEMY_NORMAL, ENEMY_LEFT, ENEMY_RIGHT;
    private CollisionObject rect;
    private static ArrayList<Bullet> enemyBullets;

    public Enemy(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        ENEMY_NORMAL = new Sprite(new Texture(GameData.Skins.Enemy.ENEMY_NORMAL));
        ENEMY_LEFT = new Sprite(new Texture(GameData.Skins.Enemy.ENEMY_LEFT));
        ENEMY_RIGHT = new Sprite(new Texture(GameData.Skins.Enemy.ENEMY_RIGHT));
        this.sprite = new Sprite(ENEMY_NORMAL);

        this.rect = new CollisionObject(x, y, width, height);

        enemyBullets = GameScreen.enemyBullets;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    @Override
    public void update() {
        x = body.getPosition().x * GameData.PPM;
        y = body.getPosition().y * GameData.PPM;
        rect.move(x, y);
    }

    public void destroyEnemy() {
        if (body.getFixtureList().size > 0) body.destroyFixture(body.getFixtureList().first());
    }

    public void shoot(Player player, World world) {
        long timer = 1000000000L;
        var enemyWidth = player.getX() < this.getX() ? -7 : 21;
        this.sprite = enemyWidth < 0 ? ENEMY_LEFT : ENEMY_RIGHT;
        if (TimeUtils.timeSinceNanos(startTime) >= timer) {
            Body body = BodyHelperService.createObjectBody(5, 5, this.getX() + enemyWidth, this.getY() + 17, world, "bullet");
            body.setUserData("EnemyBullet");
            enemyBullets.add(new Bullet(5 * 1.5f, 5 * 1.5f, body, Bullet.getBulletAngleEnemy(this, player)));
            startTime = TimeUtils.nanoTime();
        }
    }

    public CollisionObject getCollisionRect() {
        return this.rect;
    }
}