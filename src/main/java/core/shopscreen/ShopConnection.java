package core.shopscreen;

import core.shopscreen.objects.Unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ShopConnection {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    public ShopConnection() {
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

    public ArrayList<Unit> unitsList() {
        String query = "SELECT u.name, u.health_points FROM units u";

        ArrayList<Unit> unitsList = new ArrayList<>();

        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                String name = result.getString("name");
                int healthPoints = result.getInt("health_points");
                unitsList.add(new Unit(name, healthPoints));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return unitsList;
    }
}