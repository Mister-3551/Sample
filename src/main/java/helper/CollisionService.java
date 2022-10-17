package helper;

import com.badlogic.gdx.math.Rectangle;

public class CollisionService {

    public static boolean checkCollision(Rectangle playerBounds, Rectangle enemyBounds) {
        return !(playerBounds.getX() < enemyBounds.getX() - 65
                || playerBounds.getX() > enemyBounds.getX() + 65
                || playerBounds.getY() > enemyBounds.getY() + 95
                || playerBounds.getY() < enemyBounds.getY() - 95);
    }
}