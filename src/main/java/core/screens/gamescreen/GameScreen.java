package core.screens.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.GameData;
import core.gamescreen.DetectionSystem;
import core.gamescreen.helper.TileMapHelper;
import core.gamescreen.objects.bullet.Bullet;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.hostage.Hostage;
import core.gamescreen.objects.map.MapObject;
import core.gamescreen.objects.player.Player;
import core.screens.ScreenChanger;
import core.screens.navigation.NavigationBar;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private final TileMapHelper tileMapHelper;
    private final BitmapFont font;
    private final NavigationBar navigationBar;
    private Player player;
    private final Table table, empty;
    private final Stage stage;
    private static int bulletDirection;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Enemy> enemiesToRemove;
    public static ArrayList<Hostage> hostages;
    public static ArrayList<Hostage> hostagesToRemove;
    public static ArrayList<Bullet> playerBullets, enemyBullets;
    public static ArrayList<Bullet> playerBulletsToRemove, enemyBulletsToRemove;
    public static boolean moveCameraWithArrows = false;

    private static MapObjects mapObjects;

    public GameScreen(OrthographicCamera camera, String... level) {
        this.camera = camera;

        playerBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        playerBulletsToRemove = new ArrayList<>();
        enemyBulletsToRemove = new ArrayList<>();
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
        stage = new Stage(new FitViewport(0, 0));

        createStructure();
        stage.addActor(table);

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

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));

        stage.draw();

        orthogonalTiledMapRenderer.render();
        orthogonalTiledMapRenderer.setView(camera);

        batch.begin();
        player.render(batch);

        for (Enemy enemy : enemies) enemy.render(batch);
        for (Hostage hostage : hostages) hostage.render(batch);
        for (Bullet bullet : playerBullets) bullet.render(batch);
        for (Bullet bullet : enemyBullets) bullet.render(batch);

        batch.end();
        //box2DDebugRenderer.render(world, camera.combined.scl(GameData.PPM));
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(new FitViewport(width, height));
        stage.getViewport().update(width, height, true);

        camera.setToOrtho(false, width, height);

        centerCameraOnPlayer();

        GameData.SCREEN_WIDTH = width;
        GameData.SCREEN_HEIGHT = height;

        GameData.GameScreen.Camera.ORTHOGRAPHIC_CAMERA = camera;
    }

    private void update() {
        world.step(1 / 60.0f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) new ScreenChanger().changeScreen("MenuScreen");

        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            for (MapObject mapObject : GameData.GameScreen.MAP_OBJETS) {

            }
        }

        player.shoot(camera, world);

        for (Enemy enemy : enemies) {
            enemy.update();
            Enemy getEnemy = DetectionSystem.detection(enemy, player);

            if (getEnemy != null) {
                getEnemy.shoot(player, world);
            }
        }

        for (Hostage hostage : hostages) hostage.update();

        for (Bullet bullet : playerBullets) {
            bullet.update();
            if (bullet.remove) playerBulletsToRemove.add(bullet);
        }

        for (Bullet bullet : enemyBullets) {
            bullet.update();
            if (bullet.remove) enemyBulletsToRemove.add(bullet);
        }

        Collisions.checkCollisions(player);
    }

    private void cameraUpdate() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCameraWithArrows("left");
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCameraWithArrows("right");
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCameraWithArrows("up");
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCameraWithArrows("down");

        if (Gdx.input.isKeyPressed(Input.Keys.U)) centerCameraOnPlayer();

        if (!moveCameraWithArrows) {
            centerCameraOnPlayer();
            /*var playerPositionX = Math.abs(player.getBody().getPosition().x * GameData.PPM * 10) / 10f;
            var playerPositionY = Math.abs(player.getBody().getPosition().y * GameData.PPM * 10) / 10f;
            var cameraMovingPositionX = 660;
            var cameraMovingPositionY = 400;
            var mapWidth = GameData.MAP_WIDTH * 64;
            Vector3 position = camera.position;
            position.x = playerPositionX;
            position.y = playerPositionY;
        if (!GameData.GameScreen.Camera.RESET_CAMERA_POSITION) {
            if (playerPositionX > cameraMovingPositionX) position.x = playerPositionX;
            else if (playerPositionX > mapWidth - GameData.SCREEN_WIDTH / 2) position.x = playerPositionX;
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
          */
        }
    }

    private void moveCameraWithArrows(String move) {
        moveCameraWithArrows = true;
        int param = 3;
        switch (move) {
            case "left" -> camera.position.x -= param;
            case "right" -> camera.position.x += param;
            case "up" -> camera.position.y += param;
            case "down" -> camera.position.y -= param;
        }
        camera.update();
    }

    private void centerCameraOnPlayer() {
        var positionX = Math.abs(player.getBody().getPosition().x * GameData.PPM * 10) / 10f;
        var positionY = Math.abs(player.getBody().getPosition().y * GameData.PPM * 10) / 10f;

        camera.position.x = positionX;
        camera.position.y = positionY;

        camera.update();
    }

    private void createStructure() {
        table.setFillParent(true);
        table.add(navigationBar.gameScreenNavigationBar()).growX();
        table.row();
        table.add(empty).growX().growY();
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

    public static void setBulletDirection(int getBulletDirection) {
        bulletDirection = getBulletDirection;
    }

    public static void setMapObjets(MapObjects getMapObjects) {
        mapObjects = getMapObjects;
    }
}