package core.screens.navigation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import core.Constants;
import core.loginscreen.LoginConnection;
import core.screens.ScreenChanger;

public class NavigationBar {

    private Table navigation;
    private TextButton username, rank, money, empty, signOut, backToMenu, level, lives, enemy;
    private Skin skin;

    public NavigationBar() {
        navigation = new Table();
        skin = new Skin(Gdx.files.internal(Constants.SKIN));
        username = new TextButton(Constants.USERNAME, skin);
        rank = new TextButton("Rank: " + Constants.RANK, skin);
        money = new TextButton("Money: 0", skin);
        empty = new TextButton("", skin);
        signOut = new TextButton("Sign Out", skin);
        backToMenu = new TextButton("Back To Menu", skin);
        level = new TextButton("Level: 1", skin);
        lives = new TextButton("Lives: 3", skin);
        enemy = new TextButton("Enemy: 2/4", skin);
    }

    public Table menuNavigationBar() {
        navigation.add(username).fill(true).height(50.0f).growX();
        navigation.add(rank).fill(true).height(50.0f).growX();
        navigation.add(money).fill(true).height(50.0f).growX();
        navigation.add(empty).fill(true).growX();
        navigation.add(signOut).fill(true).width(200.0f).height(50.0f);

        signOut.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                new LoginConnection().signOut();
                new ScreenChanger().changeScreen("LoginScreen");
            }
        });
        return navigation;
    }

    public Table levelsAndSettingsNavigationBar() {
        navigation.add(username).fill(true).height(50.0f).growX();
        navigation.add(rank).fill(true).height(50.0f).growX();
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

    public Table gameScreenNavigationBar() {
        navigation.add(level).fill(true).height(50.0f).growX();
        navigation.add(lives).fill(true).height(50.0f).growX();
        navigation.add(enemy).fill(true).height(50.0f).growX();
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
}