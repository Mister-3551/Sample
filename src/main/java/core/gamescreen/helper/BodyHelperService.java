package core.gamescreen.helper;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.GameData;

public class BodyHelperService {

    public static Body createObjectBody(float objectWidth, float objectHeight, float x, float y, World world) {

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
        body.createFixture(bodyFixture(shape));

        shape.dispose();
        return body;
    }

    public static FixtureDef bodyFixture(PolygonShape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = shape;
        return fixtureDef;
    }

    public static void fixBleeding(TextureRegion region) {
        float fix = 0.01f;

        float x = region.getRegionX();
        float y = region.getRegionY();
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float invTexWidth = 1f / region.getTexture().getWidth();
        float invTexHeight = 1f / region.getTexture().getHeight();
        region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight, (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims
        // region
    }
}