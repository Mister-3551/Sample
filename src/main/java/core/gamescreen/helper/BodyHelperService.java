package core.gamescreen.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.GameData;

public class BodyHelperService {

    public static Body createObjectBody(float objectWidth, float objectHeight, float x, float y, World world, String type) {

        var baseUnitX = 64;
        var baseUnitY = 64;

        var width = objectWidth * 1.5f;
        var height = objectHeight * 1.5f;

        var hx = width / baseUnitX;
        var hy = height / baseUnitY;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / GameData.PPM, y / GameData.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hx, hy, new Vector2(hx, hy), 0);

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef(shape, type));

        shape.dispose();
        return body;
    }

    public static FixtureDef fixtureDef(PolygonShape shape, String type) {
        FixtureDef fixtureDef = new FixtureDef();
        if (type.matches("player") || type.matches("bullet")) fixtureDef.friction = 0;
        if (type.matches("enemy") || type.matches("hostage")) fixtureDef.friction = 20;
        fixtureDef.density = 1000;
        fixtureDef.shape = shape;
        return fixtureDef;
    }
}