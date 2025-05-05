package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
        public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlite:iotbay.db";
        return DriverManager.getConnection(url);
    }
}
