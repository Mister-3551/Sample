package core.gamescreen.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.player.Player;
import core.screen.GameScreen;

public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("maps/map0.tmx");
        parseMapObject(tiledMap.getLayers().get("Objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObject(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                    createStaticBody((PolygonMapObject) mapObject);
            }
            if (mapObject instanceof TiledMapTileMapObject) {
                if (mapObject.getName() != null && mapObject.getName().equals("Player")) {
                    float posX = ((TiledMapTileMapObject) mapObject).getX();
                    float posY = ((TiledMapTileMapObject) mapObject).getY();

                    Body body = BodyHelperService.createPlayerBody(posX, posY, gameScreen.getWorld());
                    gameScreen.setPlayer(new Player(48, 48, body));

                } else if (mapObject.getName() != null && mapObject.getName().equals("Enemy")) {
                    float posX = ((TiledMapTileMapObject) mapObject).getX();
                    float posY = ((TiledMapTileMapObject) mapObject).getY();

                    Body body = BodyHelperService.createPlayerBody(posX, posY, gameScreen.getWorld());
                    gameScreen.setEnemy(new Enemy(48, 48, body));
                }
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}