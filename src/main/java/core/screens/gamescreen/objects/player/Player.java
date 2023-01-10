package core.screens.gamescreen.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.GameData;
import core.screens.gamescreen.helper.BodyHelperService;
import core.screens.gamescreen.helper.CollisionObject;
import core.screens.gamescreen.objects.bullet.Bullet;
import core.screens.gamescreen.GameScreen;

import java.util.ArrayList;

public class Player extends PlayerEntity {

    private int jumpCounter;
    private Sprite sprite;
    private final Sprite PLAYER_NORMAL, PLAYER_LEFT_SWORD, PLAYER_RIGHT_SWORD;
    private CollisionObject rect, rectRight, rectLeft;
    private static ArrayList<Bullet> playerBullets;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
        this.jumpCounter = 0;
        PLAYER_NORMAL = new Sprite(new Texture(GameData.Skins.Player.PLAYER_NORMAL));
        GameData.Player.Sprite.PLAYER_LEFT_SPRITE = new Sprite(new Texture(GameData.Skins.Player.PLAYER_LEFT));
        PLAYER_LEFT_SWORD = new Sprite(new Texture(GameData.Skins.Player.PLAYER_LEFT_SWORD));
        GameData.Player.Sprite.PLAYER_RIGHT_SPRITE = new Sprite(new Texture(GameData.Skins.Player.PLAYER_RIGHT));
        PLAYER_RIGHT_SWORD = new Sprite(new Texture(GameData.Skins.Player.PLAYER_RIGHT_SWORD));
        this.sprite = new Sprite(PLAYER_NORMAL);
        this.rect = new CollisionObject(x, y, width, height);

        this.rectLeft = new CollisionObject(x, y, width, height);
        this.rectRight = new CollisionObject(x, y, 22 * 1.5f, height);

        playerBullets = GameScreen.playerBullets;
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
        checkUserInput();
        if (Bullet.diffX(GameData.GameScreen.Camera.ORTHOGRAPHIC_CAMERA, this) < 0) setSprite(GameData.Player.Sprite.PLAYER_LEFT_SPRITE);
        else setSprite(GameData.Player.Sprite.PLAYER_RIGHT_SPRITE);
    }

    private void checkUserInput() {
        velX = 0;
        if (Gdx.input.isKeyPressed(GameData.Player.PLAYER_KEY_LEFT)) {
            if (!this.sprite.equals(GameData.Player.Sprite.PLAYER_LEFT_SPRITE)) {
                GameScreen.moveCameraWithArrows = false;
                //setSprite(PLAYER_LEFT);
                GameScreen.setBulletDirection(-3);
                //float baseX = 14f, baseY = 34f;
                //changeShape(baseX, baseY);
                this.rect = rectLeft;
            }
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(GameData.Player.PLAYER_KEY_RIGHT)) {
            if (!this.sprite.equals(GameData.Player.Sprite.PLAYER_RIGHT_SPRITE)) {
                GameScreen.moveCameraWithArrows = false;
                //setSprite(PLAYER_RIGHT_SWORD);
                GameScreen.setBulletDirection(3);
                //float baseX = 22f, baseY = 34f;
                //changeShape(baseX, baseY);
                this.rect = rectRight;
            }
            velX = 1;
        }

        if (Gdx.input.isKeyJustPressed(GameData.Player.PLAYER_KEY_JUMP) && jumpCounter < 25) {
            GameScreen.moveCameraWithArrows = false;
            float force = body.getMass() * 12;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCounter++;
        }
        if (body.getLinearVelocity().y == 0) jumpCounter = 0;
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    public void shoot(OrthographicCamera camera, World world) {
        if (Gdx.input.isButtonJustPressed(GameData.Player.PLAYER_KEY_SHOOT) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            var playerWidth = Bullet.diffX(camera, this) < 0 ? -7 : 21;
            Body body = BodyHelperService.createObjectBody(5, 5, this.getX() + playerWidth, this.getY() + 17, world, "bullet");
            body.setUserData("bullet");
            playerBullets.add(new Bullet(5 * 1.5f, 5 * 1.5f, body, Bullet.getBulletAngle(this, camera)));
        }
    }


    private void changeShape(float baseX, float baseY) {
        int baseUnitX = 64, baseUnitY = 64;

        height = baseY * 1.5f;
        width = baseX * 1.5f;

        var hx = width / baseUnitX;
        var hy = height / baseUnitY;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx, hy, new Vector2(hx, hy), 0);

        Fixture previous = body.getFixtureList().first();

        body.destroyFixture(previous);
        body.createFixture(BodyHelperService.fixtureDef(shape, "player"));

        shape.dispose();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public CollisionObject getCollisionRect() {
        return this.rect;
    }
}