package core.screens.navigation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import core.GameData;
import core.screens.ScreenChanger;

public class NavigationBar {

    private Table navigation;
    private TextButton username, rank, money, inventory, shop, empty, backToMenu, level, enemy, hostage;
    private Skin skin;

    public NavigationBar() {
        navigation = new Table();
        skin = new Skin(Gdx.files.internal(GameData.Skins.SKIN));
        username = new TextButton(GameData.Player.PLAYER_USERNAME, skin);
        rank = new TextButton("Rank: " + GameData.Player.PLAYER_RANK, skin);
        money = new TextButton("Money: 0", skin);
        inventory = new TextButton("Inventory", skin);
        shop = new TextButton("Shop", skin);
        empty = new TextButton("", skin);
        backToMenu = new TextButton("Back To Menu", skin);

        level = new TextButton("Level: " + GameData.CURRENT_LEVEL, skin);
        enemy = new TextButton("Enemy: " + GameData.GameScreen.ENEMY_KILLS + "/4", skin);
        hostage = new TextButton("Hostage: 0/4", skin);
    }

    public Table menuNavigationBar() {
        navigation.add(username).width(200.0f).height(50.0f);
        navigation.add(rank).width(200.0f).height(50.0f);
        navigation.add(money).width(200.0f).height(50.0f);
        navigation.add(empty).height(50.0f).growX();
        navigation.add(inventory).width(200.0f).height(50);
        navigation.add(shop).width(200.0f).height(50);

        inventory.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new ScreenChanger().changeScreen("InventoryScreen");
            }
        });

        shop.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("http://localhost:3000/shop");
            }
        });
        return navigation;
    }

    public Table basicNavigationBar() {
        navigation.add(username).width(200.0f).height(50.0f);
        navigation.add(rank).width(200.0f).height(50.0f);
        navigation.add(money).width(200.0f).height(50.0f);
        navigation.add(empty).height(50.0f).growX();
        navigation.add(backToMenu).width(200.0f).height(50.0f);

        backToMenu.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("MenuScreen");
            }
        });
        return navigation;
    }

    public Table gameScreenNavigationBar() {
        level.setText("Level " + GameData.CURRENT_LEVEL);
        navigation.add(level).fill(true).width(200.0f).height(50.0f);
        navigation.add(enemy).fill(true).width(200.0f).height(50.0f);
        navigation.add(hostage).fill(true).width(200.0f).height(50.0f);
        navigation.add(money).fill(true).width(200.0f).height(50.0f);
        navigation.add(empty).height(50.0f).growX();
        navigation.add(backToMenu).width(200.0f).height(50.0f);

        backToMenu.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("MenuScreen");
            }
        });
        return navigation;
    }

    public void updateEnemyKills() {
        this.enemy.setText("Enemy: " + (GameData.GameScreen.ENEMY_KILLS += 1) + "/4");
    }
}