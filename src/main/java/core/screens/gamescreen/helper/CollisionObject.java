package core.screens.gamescreen.helper;

public class CollisionObject {

    float x, y, width, height;

    public CollisionObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith(CollisionObject rect) {
        return x < rect.x + rect.width + 1
                && y < rect.y + rect.height + 1
                && x + width + 1 > rect.x
                && y + height + 1 > rect.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}