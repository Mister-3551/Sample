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
        String query = "SELECT levels.name, levels.map, levels_users.completed FROM levels \n" +
                "INNER JOIN levels_users ON levels_users.id_levels = levels.id\n" +
                "INNER JOIN users ON users.id = levels_users.id_users\n" +
                "WHERE users.username = '" + Constants.USERNAME + "'";

        ArrayList<Level> levelsList = new ArrayList<>();

        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                String name = result.getString("name");
                String map = result.getString("map");
                String completed = result.getInt("completed") == 0 ? "uncompleted" : "completed";
                levelsList.add(new Level(name, map, completed));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return levelsList;
    }
}