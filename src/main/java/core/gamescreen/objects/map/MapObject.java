package core.gamescreen.objects.map;

import core.gamescreen.helper.CollisionService;

public class MapObject {

    CollisionService rect;

    String i;

    public MapObject(String i, float x, float y, float height, float width) {
        this.rect = new CollisionService(x, y, width, height);
        this.i = i;
    }

    public CollisionService getCollisionRect() {
        return rect;
    }

    public String getI() {
        return i;
    }
}