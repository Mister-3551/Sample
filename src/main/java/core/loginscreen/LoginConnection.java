package core.loginscreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getException());
        }
    }

    public boolean checkIfUserExists(String username, String password) {
        String query = "SELECT id FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                if (!result.getString("id").isEmpty()) return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}