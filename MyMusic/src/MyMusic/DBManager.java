package MyMusic;

import javax.management.Query;
import java.sql.*;

// Hides the database from the controllers
// Bridge between controllers and the database
public class DBManager {
    private String url;

    public DBManager(String url)
    {
        this.url = url;
    }

    public User getUser(String un) {
        User user = new User();
        String query = "";

        try {
            // Open the connection to the database
            Connection connection = DriverManager.getConnection(url);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User login(String un, String pwd) {
        User user = new User();
        String query = "";

        try {
            // Open the connection to the database
            Connection connection = DriverManager.getConnection(url);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the query
            ResultSet rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}