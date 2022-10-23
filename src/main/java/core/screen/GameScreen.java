package core.screen;

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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import core.gamescreen.helper.CollisionService;
import core.Constants;
import core.gamescreen.helper.TileMapHelper;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.player.Player;
import static core.Constants.PPM;

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

    private int lives, kills, deaths;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f) , false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        this.lives = 3;
        this.kills = 0;
        this.deaths = 0;

        this.font = new BitmapFont();
        font.setColor(Color.ORANGE);
        font.getData().setScale(2, 2);
    }

    @Override
    public void render(float delta) {
        this.update();

        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(211, 211, 211, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();
        player.render(batch);
        enemy.render(batch);

        //font.draw(batch, "LIVES: " + lives, camera.position.x - 450, camera.position.y + 350);
        //font.draw(batch, "KILLS: " + kills, camera.position.x - 275, camera.position.y + 350);
        //font.draw(batch, "DEATHS: " + deaths, camera.position.x - 75, camera.position.y + 350);
        font.draw(batch, Constants.USERNAME + ": " + kills, camera.position.x - (Constants.SCREENWIDTH / 2f) + 50, camera.position.y + (Constants.SCREENHEIGHT / 2f) - 50);
        font.draw(batch, "Player : 0", camera.position.x - (Constants.SCREENWIDTH / 2f) + 50, camera.position.y + (Constants.SCREENHEIGHT / 2f) - 100);

        batch.end();
        //box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        enemy.update();

        if (CollisionService.checkCollision(player.getPlayerBounds(), enemy.getEnemyBounds())) kills++;

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = Math.abs(player.getBody().getPosition().x * PPM * 10) / 10f;
        position.y = Math.abs(player.getBody().getPosition().y * PPM * 10) / 10f;
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
}