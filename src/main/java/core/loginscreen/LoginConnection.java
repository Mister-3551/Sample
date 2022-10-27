package core.loginscreen;

import core.Constants;
import core.xml.XmlFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.UUID;

public class LoginConnection {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    public LoginConnection() {
        url = "jdbc:mysql://localhost:3306/javafx";
        username = "root";
        password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkIfUserExists(String username, String password) {
        String query = "SELECT users.id FROM users WHERE users.username = '" + username + "' AND users.password = '" + password + "'";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                if (!result.getString("id").isEmpty()) {
                    String gameToken = createGameToken();
                    String gameTokenQuery = "UPDATE users SET users.game_token = '" + gameToken + "' WHERE users.id = '" + result.getString("id") + "'";
                    connection.createStatement().executeUpdate(gameTokenQuery);
                    Constants.GAMETOKEN = gameToken;
                    setUserData();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean checkIfUserSignIn() {
        if (Constants.GAMETOKEN == null) new XmlFile().getUserData();
        if (Constants.GAMETOKEN.equals("")) return false;
        String query = "SELECT users.game_token FROM users WHERE users.game_token = '" + Constants.GAMETOKEN + "'";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                String gameToken = result.getString("game_token");
                if (gameToken.equals(Constants.GAMETOKEN)) {
                    setUserData();
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void signOut() {
        try {
            String gameTokenQuery = "UPDATE users SET users.game_token = '' WHERE users.game_token = '" + Constants.GAMETOKEN + "'";
            connection.createStatement().executeUpdate(gameTokenQuery);
            Constants.GAMETOKEN = "";
            new XmlFile().deleteGameToken();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setUserData() {
        String query = "SELECT users.username, users.rank FROM users WHERE users.game_token = '" + Constants.GAMETOKEN + "'";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                Constants.USERNAME = result.getString("username");
                Constants.RANK = result.getString("rank");
            }
            new XmlFile().createXmlFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String createGameToken() {
        return String.valueOf(System.currentTimeMillis()) + UUID.randomUUID();
    }
}