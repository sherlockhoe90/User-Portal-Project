package com.inexture.userportal.userportalproject.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static Logger logger = LogManager.getLogger("AgeCalculatorUtility");
    private static final String url = "jdbc:mysql://localhost:3306/inexturesolutionstraining_27_06_23";
    private static final String username = "root";
    private static final String password = "root";
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // only creates a new connection if one doesn't exist, or is closed
            // singleton patter design pattern
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.error("class not found exception has occurred.");
            e.getMessage();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}
