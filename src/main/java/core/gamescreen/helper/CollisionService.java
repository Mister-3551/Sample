package core.gamescreen.helper;

public class CollisionService {

    float x, y, width, height;

    public CollisionService(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(CollisionService rect) {
        return x < rect.x + rect.width
                && y < rect.y + rect.height
                && x + width > rect.x
                && y + height > rect.y;
    }
}