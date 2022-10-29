package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import core.Constants;
import core.gamescreen.helper.BodyHelperService;
import core.gamescreen.helper.TileMapHelper;
import core.gamescreen.objects.bullet.Bullet;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.player.Player;
import core.screens.navigation.NavigationBar;

import java.util.ArrayList;

import static core.Constants.*;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private BitmapFont font;

    // game core.objects
    private Player player;
    private Enemy enemy;
    private ArrayList<Bullet> bullets, bulletsToRemoveve;
    private Table table, empty;
    private Stage stage;

    private int lives, kills, deaths;
    private static int bulletDirection;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -25f) , false);
        box2DDebugRenderer = new Box2DDebugRenderer();
        tileMapHelper = new TileMapHelper(this);
        orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        table = new Table();
        empty = new Table();

        lives = 3;
        kills = 0;
        deaths = 0;

        stage = new Stage();
        createStructure();

        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        font.setColor(Color.ORANGE);
        font.getData().setScale(2, 2);

        bullets = new ArrayList<>();
        bulletsToRemoveve = new ArrayList<>();
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


        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
        //enemy.render(batch);

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        //enemy.update();

        //if (CollisionService.checkCollision(player.getPlayerBounds(), enemy.getEnemyBounds())) kills++;

        //if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) new ScreenChanger().changeScreen("MenuScreen");

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            var playerWidth = 15;
            if (bulletDirection == -3) playerWidth = -15;
            Body body = BodyHelperService.createObjectBody(5, 5,getPlayer().getBody().getPosition().x * Constants.PPM + playerWidth, getPlayer().getBody().getPosition().y * Constants.PPM + 17, world);
            bullets.add(new Bullet(5 * 1.5f, 5 * 1.5f, body, bulletDirection));
        }

        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.remove) bulletsToRemoveve.add(bullet);
        }
        bullets.removeAll(bulletsToRemoveve);
        if (bullets.size() == 0 && bulletsToRemoveve.size() != 0) bulletsToRemoveve.clear();
    }

    private void cameraUpdate() {
        var playerPositionX = Math.abs(player.getBody().getPosition().x * Constants.PPM * 10) / 10f;
        var playerPositionY = Math.abs(player.getBody().getPosition().y * Constants.PPM * 10) / 10f;
        var cameraMovingPosition = 660;

        Vector3 position = camera.position;
        if (playerPositionX > cameraMovingPosition) position.x = playerPositionX;
        else position.x = cameraMovingPosition;
        if (playerPositionY > 400) position.y = playerPositionY;
        camera.position.set(position);
        camera.update();
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return this.player;
    }
    public void setPlayer(Player player) {
        this.player = player;
        this.player.setSprite(new Sprite(new Texture(Constants.PLAYER_NORMAL)));
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        this.enemy.setSprite(new Sprite(new Texture(Constants.ENEMY_NORMAL)));
    }

    public static void setBulletDirection(int bulletDirection1) {
        bulletDirection = bulletDirection1;
    }

    private void createStructure() {
        table.setFillParent(true);
        table.add(new NavigationBar().gameScreenNavigationBar()).growX();
        table.row();
        table.add(empty).growX().growY();

        stage.addActor(table);
    }
}