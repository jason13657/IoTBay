package uts.isd.iotbay.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private Connection conn;
    // For SQLite, the URL will be like: jdbc:sqlite:iotbay.db
    // You might want to make this configurable, e.g., read from a properties file
    private static final String DB_URL = "jdbc:sqlite:iotbay.db"; // Assumes iotbay.db is in the project root
    // If your pom.xml has the driver, this is usually not needed for modern JDBC drivers
    // private static final String DB_DRIVER = "org.sqlite.JDBC";

    public DBConnector() throws ClassNotFoundException, SQLException {
        // Modern JDBC drivers (Type 4) often don't need Class.forName() anymore
        // if the JAR is in the classpath (managed by Maven).
        // Class.forName(DB_DRIVER);
        this.conn = DriverManager.getConnection(DB_URL);
    }

    public Connection getConnection() {
        // You might want to add logic here to check if the connection is still valid
        // and re-establish if necessary, especially for web applications.
        return this.conn;
    }

    public void closeConnection() throws SQLException {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
        }
    }
} 