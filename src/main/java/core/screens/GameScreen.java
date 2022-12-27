package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import core.GameData;
import core.gamescreen.DetectionSystem;
import core.gamescreen.helper.BodyHelperService;
import core.gamescreen.helper.TileMapHelper;
import core.gamescreen.objects.bullet.Bullet;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.hostage.Hostage;
import core.gamescreen.objects.player.Player;
import core.screens.navigation.NavigationBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private BitmapFont font;
    private NavigationBar navigationBar;
    private Player player;
    private Table table, empty;
    private Stage stage;
    private static int bulletDirection;
    public ArrayList<Enemy> enemies;
    public ArrayList<Enemy> enemiesToRemove;
    public ArrayList<Hostage> hostages;
    public ArrayList<Hostage> hostagesToRemove;
    private final ArrayList<Bullet> bullets;
    private final ArrayList<Bullet> bulletsToRemove;
    private Long startTime = TimeUtils.nanoTime();
    private final Long timer = 1000000000L;
    private int bulletRotate;

    public GameScreen(OrthographicCamera camera, String... level) {
        this.camera = camera;

        bullets = new ArrayList<>();
        bulletsToRemove = new ArrayList<>();
        enemies = new ArrayList<>();
        enemiesToRemove = new ArrayList<>();
        hostages = new ArrayList<>();
        hostagesToRemove = new ArrayList<>();

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -25f), false);
        box2DDebugRenderer = new Box2DDebugRenderer();
        tileMapHelper = new TileMapHelper(this);
        orthogonalTiledMapRenderer = tileMapHelper.setupMap(level);

        navigationBar = new NavigationBar();

        table = new Table();
        empty = new Table();

        stage = new Stage();
        createStructure();

        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        font.setColor(Color.ORANGE);
        font.getData().setScale(2, 2);

        Pixmap pm = new Pixmap(Gdx.files.internal(GameData.Skins.Cursor.AIM_CURSOR));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();

        GameData.GameScreen.GAME_SCREEN = this;
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(211, 211, 211, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();

        batch.begin();
        player.render(batch);

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        for (Hostage hostage : hostages) {
            hostage.render(batch);
        }

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }

        batch.end();
        //box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) new ScreenChanger().changeScreen("MenuScreen");

        if (Gdx.input.isButtonJustPressed(GameData.Player.PLAYER_KEY_SHOOT) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            var playerWidth = 20;
            if (Bullet.diffX(camera, player) < 0) playerWidth = -6;
            Body body = BodyHelperService.createObjectBody(5, 5, player.getX() + playerWidth, player.getY() + 17, world);
            bullets.add(new Bullet(5 * 1.5f, 5 * 1.5f, body, Bullet.getBulletAngle(player, camera)));
        }

        for (Enemy enemy : enemies) {
            enemy.update();
            Enemy getEnemy = DetectionSystem.detection(enemy, player);

            if (getEnemy != null) {
                enemyShoot(getEnemy);
            }
        }

        for (Hostage hostage : hostages) {
            hostage.update();
        }

        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.remove) bulletsToRemove.add(bullet);
        }

        checkCollisions();
    }

    private void enemyShoot(Enemy enemy) {
        if (TimeUtils.timeSinceNanos(startTime) >= timer) {

            bulletRotate = player.getX() < enemy.getX() ? -30 : 30;

            Body body = BodyHelperService.createObjectBody(5, 5, enemy.getX() + bulletRotate, enemy.getY() + 17, world);
            bullets.add(new Bullet(5 * 1.5f, 5 * 1.5f, body, Bullet.getBulletAngleEnemy(enemy, player, camera)));
            startTime = TimeUtils.nanoTime();
        }
    }

    private void cameraUpdate() {
        var playerPositionX = Math.abs(player.getBody().getPosition().x * GameData.PPM * 10) / 10f;
        var playerPositionY = Math.abs(player.getBody().getPosition().y * GameData.PPM * 10) / 10f;

        var cameraMovingPositionX = 660;
        var cameraMovingPositionY = 400;

        var mapWidth = GameData.MAP_WIDTH * 64;

        Vector3 position = camera.position;

        if (!GameData.GameScreen.Camera.RESET_CAMERA_POSITION) {
            if (playerPositionX > cameraMovingPositionX) position.x = playerPositionX;
            else if (playerPositionX > mapWidth - GameData.SCREENWIDTH / 2) position.x = playerPositionX;
            else position.x = cameraMovingPositionX;
            if (playerPositionY > cameraMovingPositionY) position.y = playerPositionY;
        } else {
            position.x = playerPositionX;
            if (playerPositionX < cameraMovingPositionX) position.x = cameraMovingPositionX;
            if (playerPositionY < cameraMovingPositionY) position.y = cameraMovingPositionY;
            GameData.GameScreen.Camera.RESET_CAMERA_POSITION = false;
        }
        camera.position.set(position);
        camera.update();
    }

    private void checkCollisions() {
        for (Enemy enemy : enemies) {
            for (Bullet bullet : bullets) {
                if (enemy.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                    bullet.destroyBullet();
                    enemy.destroyEnemy();
                    bulletsToRemove.add(bullet);
                    enemiesToRemove.add(enemy);
                    navigationBar.updateEnemyKills();
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);

        for (Hostage hostage : hostages) {
            for (Bullet bullet : bullets) {
                if (hostage.getCollisionRect().collidesWith(bullet.getCollisionRect())) {
                    bullet.destroyBullet();
                    hostage.destroyHostage();
                    bulletsToRemove.add(bullet);
                    hostagesToRemove.add(hostage);
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        hostages.removeAll(hostagesToRemove);

        for (Hostage hostage : hostages) {
            if (hostage.getCollisionRect().collidesWith(player.getCollisionRect())) {
                hostage.destroyHostage();
                hostagesToRemove.add(hostage);
            }
        }
        hostages.removeAll(hostagesToRemove);

        if (bullets.size() == 0 && bulletsToRemove.size() != 0) bulletsToRemove.clear();
        if (enemies.size() == 0 && enemiesToRemove.size() != 0) enemiesToRemove.clear();
        if (hostages.size() == 0 && hostagesToRemove.size() != 0) hostagesToRemove.clear();
    }

    private void createStructure() {
        table.setFillParent(true);
        table.add(navigationBar.gameScreenNavigationBar()).growX();
        table.row();
        table.add(empty).growX().growY();

        stage.addActor(table);
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.player.setSprite(new Sprite(new Texture(GameData.Skins.Player.PLAYER_NORMAL)));
    }

    public static void setBulletDirection(int bulletDirection1) {
        bulletDirection = bulletDirection1;
    }
}