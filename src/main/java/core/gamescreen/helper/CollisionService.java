package core.gamescreen.helper;

import com.badlogic.gdx.math.Rectangle;

public class CollisionService {

    private static float x, y;
    private static float width, height;

    public CollisionService(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static void move(float px, float py) {
        x = px;
        y = py;
    }

    public static boolean checkCollision(CollisionService body) {
        return x < body.width && y < body.y + body.height && x + width > body.x && y + height > body.height;
    }
}