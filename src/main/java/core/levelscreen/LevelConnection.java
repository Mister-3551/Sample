package core.levelscreen;

import core.Constants;
import core.levelscreen.objects.Level;
import core.xml.XmlFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

public class LevelConnection {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    public LevelConnection() {
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

    public ArrayList<Level> levelsList() {
        String query = "SELECT l.id, l.name, l.map, CASE WHEN user_levels.completed IS NULL THEN 0 ELSE user_levels.completed END as completed " +
                "FROM levels l " +
                "LEFT JOIN (SELECT lu.id_levels as id_levels, lu.completed as completed " +
                "FROM levels_users lu " +
                "JOIN users ON users.id = lu.id_users " +
                "WHERE users.username = ?) as user_levels ON user_levels.id_levels = l.id ";

        ArrayList<Level> levelsList = new ArrayList<>();

        try {
            var statement = connection.prepareStatement(query);
            statement.setString(1, Constants.USERNAME);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                String map = result.getString("map");
                int completed = result.getInt("completed");
                levelsList.add(new Level(name, map, completed));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return levelsList;
    }
}