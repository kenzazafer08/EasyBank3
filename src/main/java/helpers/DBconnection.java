package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static DBconnection instance;
    private static final String jdbcUrl="jdbc:postgresql://localhost:5432/Easybank";
    private static final String username="postgres";
    private static final String password= "1234";
    private Connection connection;
    private DBconnection(){}
    public static synchronized DBconnection getInstance() {
        if (instance == null) {
            instance = new DBconnection();
        }
        return instance;
    }

    // Connect to the database
    public void connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
  }

    // Get the database connection
    public Connection getConnection() {
        try {
            connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Close the database connection
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Connection closed.");
        }
    }

}


