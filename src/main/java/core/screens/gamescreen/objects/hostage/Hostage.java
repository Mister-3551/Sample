package core.screens.gamescreen.objects.hostage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.GameData;
import core.screens.gamescreen.helper.CollisionObject;

public class Hostage extends HostageEntity {
    private Sprite sprite;
    private final Sprite HOSTAGE_NORMAL, HOSTAGE_LEFT, HOSTAGE_RIGHT;

    private CollisionObject rect;

    public Hostage(float width, float height, Body body) {
        super(width, height, body);
        HOSTAGE_NORMAL = new Sprite(new Texture(GameData.Skins.Hostage.HOSTAGE_NORMAL));
        HOSTAGE_LEFT = new Sprite(new Texture(GameData.Skins.Hostage.HOSTAGE_LEFT));
        HOSTAGE_RIGHT = new Sprite(new Texture(GameData.Skins.Hostage.HOSTAGE_RIGHT));
        this.sprite = new Sprite(HOSTAGE_NORMAL);

        this.rect = new CollisionObject(x, y, width, height);
    }

    @Override
    public void update() {
        x = body.getPosition().x * GameData.PPM;
        y = body.getPosition().y * GameData.PPM;
        rect.move(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    public void destroyHostage() {
        if (!body.getFixtureList().isEmpty()) body.destroyFixture(body.getFixtureList().first());
    }

    public CollisionObject getCollisionRect() {
        return this.rect;
    }
}