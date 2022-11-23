package core.screens.navigation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import core.Constants;
import core.PlayerData;
import core.loginscreen.LoginConnection;
import core.screens.ScreenChanger;

public class NavigationBar {

    private Table navigation;
    private TextButton username, rank, money, shop, empty, backToMenu, level, enemy, hostage;
    private Skin skin;

    public NavigationBar() {
        navigation = new Table();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        username = new TextButton(PlayerData.PLAYER_USERNAME, skin);
        rank = new TextButton("Rank: " + PlayerData.PLAYER_RANK, skin);
        money = new TextButton("Money: 0", skin);
        shop = new TextButton("Shop", skin);
        empty = new TextButton("", skin);
        backToMenu = new TextButton("Back To Menu", skin);

        level = new TextButton("Level: " + PlayerData.CURRENT_LEVEL, skin);
        enemy = new TextButton("Enemy: " + Constants.ENEMY_KILLS + "/4", skin);
        hostage = new TextButton("Hostage: 0/4", skin);
    }

    public Table menuNavigationBar() {
        navigation.add(username).fill(true).height(50.0f).growX();
        navigation.add(rank).fill(true).height(50.0f).growX();
        navigation.add(money).fill(true).height(50.0f).growX();
        navigation.add(empty).fill(true).height(50).width(200);
        navigation.add(shop).fill(true).height(50).width(200);

        shop.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("ShopScreen");
            }
        });
        return navigation;
    }

    public Table basicNavigationBar() {
        navigation.add(username).fill(true).height(50.0f).growX();
        navigation.add(rank).fill(true).height(50.0f).growX();
        navigation.add(money).fill(true).height(50.0f).growX();
        navigation.add(empty).fill(true).width(200);
        navigation.add(backToMenu).fill(true).width(200.0f).height(50.0f);

        backToMenu.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("MenuScreen");
            }
        });
        return navigation;
    }

    public Table gameScreenNavigationBar() {
        level.setText("Level " + PlayerData.CURRENT_LEVEL);
        navigation.add(level).fill(true).height(50.0f).growX();
        navigation.add(enemy).fill(true).height(50.0f).growX();
        navigation.add(hostage).fill(true).height(50.0f).growX();
        navigation.add(money).fill(true).height(50.0f).growX();
        navigation.add(empty).fill(true).growX();
        navigation.add(backToMenu).fill(true).width(200.0f).height(50.0f);

        backToMenu.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new ScreenChanger().changeScreen("MenuScreen");
            }
        });
        return navigation;
    }

    public void updateEnemyKills() {
        this.enemy.setText("Enemy: " + (Constants.ENEMY_KILLS += 1) + "/4");
    }
}