package core.gamescreen.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.GameData;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.hostage.Hostage;
import core.gamescreen.objects.player.Player;
import core.screens.GameScreen;

public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap(String... level) {
        GameData.GameScreen.Camera.RESET_CAMERA_POSITION = true;
        String map = level.length == 0 ? GameData.LevelScreen.LEVEL_LIST.get(GameData.CURRENT_LEVEL - 1).getMap() : level[0];
        tiledMap = new TmxMapLoader().load("maps/" + map + ".tmx");
        MapProperties prop = tiledMap.getProperties();

        GameData.MAP_WIDTH = prop.get("width", Integer.class);
        GameData.MAP_HEIGHT = prop.get("height", Integer.class);

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

                    Body body = BodyHelperService.createObjectBody(14, 34, posX, posY, gameScreen.getWorld());
                    gameScreen.setPlayer(new Player((14 * 1.5f), (34 * 1.5f), body));
                }
                else if (mapObject.getName() != null && mapObject.getName().equals("Enemy")) {
                    float posX = ((TiledMapTileMapObject) mapObject).getX();
                    float posY = ((TiledMapTileMapObject) mapObject).getY();

                    Body body = BodyHelperService.createObjectBody(14, 34, posX, posY, gameScreen.getWorld());
                    gameScreen.enemies.add(new Enemy(14 * 1.5f, 34 * 1.5f, body));
                }
                else if (mapObject.getName() != null && mapObject.getName().equals("Hostage")) {

                    for (int i = 0; i < 10; i++) {
                        float posX = ((TiledMapTileMapObject) mapObject).getX() + i * 4;
                        float posY = ((TiledMapTileMapObject) mapObject).getY() + i * 100;

                        Body body = BodyHelperService.createObjectBody(14, 34, posX, posY, gameScreen.getWorld());
                        gameScreen.hostages.add(new Hostage(14 * 1.5f, 34 * 1.5f, body));
                    }
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
            Vector2 current = new Vector2(vertices[i * 2] / GameData.PPM, vertices[i * 2 + 1] / GameData.PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}