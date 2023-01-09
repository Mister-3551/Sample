package core.gamescreen.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.GameData;
import core.downloadfile.DownloadFile;
import core.gamescreen.objects.enemy.Enemy;
import core.gamescreen.objects.hostage.Hostage;
import core.gamescreen.objects.player.Player;
import core.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class TileMapHelper {

    public static TiledMap tiledMap;
    private GameScreen gameScreen;
    private ArrayList<core.gamescreen.objects.map.MapObject> mapObjects;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        mapObjects = new ArrayList<>();
    }

    public OrthogonalTiledMapRenderer setupMap(String... level) {
        GameData.GameScreen.Camera.RESET_CAMERA_POSITION = true;
        String map = level.length == 0 ? GameData.LevelScreen.LEVEL_LIST.get(GameData.CURRENT_LEVEL - 1).getMap() : level[0];

        temporarySkin();
        String mapDirectory = DownloadFile.getLevelMaps(map);

        tiledMap = new TmxMapLoader().load(mapDirectory + GameData.Directory.TEMPORARY_MAP_NAME);
        MapProperties prop = tiledMap.getProperties();

        GameData.MAP_WIDTH = prop.get("width", Integer.class);
        GameData.MAP_HEIGHT = prop.get("height", Integer.class);

        MapObjects objects = tiledMap.getLayers().get("Objects").getObjects();
        parseMapObject(objects);

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObject(MapObjects mapObjects) {
        int countEnemies = 0, countHostages = 0;

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
            if (mapObject instanceof TiledMapTileMapObject) {
                if (mapObject.getName() != null && mapObject.getName().equals("Player")) {
                    float posX = ((TiledMapTileMapObject) mapObject).getX();
                    float posY = ((TiledMapTileMapObject) mapObject).getY();

                    Body body = BodyHelperService.createObjectBody(14, 34, posX, posY, gameScreen.getWorld(), "player");
                    gameScreen.setPlayer(new Player((14 * 1.5f), (34 * 1.5f), body));
                }
                else if (mapObject.getName() != null && mapObject.getName().equals("Enemy")) {
                    float posX = ((TiledMapTileMapObject) mapObject).getX();
                    float posY = ((TiledMapTileMapObject) mapObject).getY();

                    Body body = BodyHelperService.createObjectBody(14, 34, posX, posY, gameScreen.getWorld(), "enemy");
                    gameScreen.enemies.add(new Enemy(14 * 1.5f, 34 * 1.5f, body));

                    countEnemies++;
                }
                else if (mapObject.getName() != null && mapObject.getName().equals("Hostage")) {
                    float posX = ((TiledMapTileMapObject) mapObject).getX();
                    float posY = ((TiledMapTileMapObject) mapObject).getY();

                    Body body = BodyHelperService.createObjectBody(14, 34, posX, posY, gameScreen.getWorld(), "hostage");
                    gameScreen.hostages.add(new Hostage(14 * 1.5f, 34 * 1.5f, body));

                    countHostages++;
                }
            }
        }
        GameData.GameScreen.Statistics.MAX_ENEMIES = countEnemies;
        GameData.GameScreen.Statistics.MAX_HOSTAGE = countHostages;
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

    private void temporarySkin() {

        String playerDirectory = DownloadFile.getSkins("player", "green");
        String enemyDirectory = DownloadFile.getSkins("enemy", "basic");
        String hostageDirectory = DownloadFile.getSkins("hostage", "basic");

        GameData.Skins.Player.PLAYER_NORMAL = playerDirectory + "/player-stand.png";
        GameData.Skins.Player.PLAYER_LEFT = playerDirectory + "/player-left.png";
        GameData.Skins.Player.PLAYER_RIGHT = playerDirectory + "/player-right.png";

        GameData.Skins.Enemy.ENEMY_NORMAL = enemyDirectory + "/enemy-stand.png";
        GameData.Skins.Enemy.ENEMY_LEFT = enemyDirectory + "/enemy-left.png";
        GameData.Skins.Enemy.ENEMY_RIGHT = enemyDirectory + "/enemy-right.png";

        GameData.Skins.Hostage.HOSTAGE_NORMAL = hostageDirectory + "/hostage-stand.png";
        GameData.Skins.Hostage.HOSTAGE_LEFT = hostageDirectory + "/hostage-left.png";
        GameData.Skins.Hostage.HOSTAGE_RIGHT = hostageDirectory + "/hostage-right.png";
    }

    public static TiledMap getTiledMap() {
        return tiledMap;
    }
}