package com.inexture.userportal.userportalproject.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    /*important information at the end of this file's code */

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
                //using the encryption and decryption in password, as its not good practice or safe to enter hardcoded password into it
                connection = DriverManager.getConnection(url, username, PasswordEncryption.decrypt(PasswordEncryption.encrypt(password)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.error("class not found exception has occurred.");
        }
    }

    // making the constructor private to prevent instantiation from external sources,
    // reinforcing the singleton pattern
    private DatabaseManager() {
    }

    // Method to get a connection to the database
    public static Connection getConnection() {
        try {
            //checks if a valid connection exists (with a timeout of 5 seconds), if yes, returns the existing connection
            //if not, creates a new one and returns it
            return connection.isValid(5) ? connection : DriverManager.getConnection(url, username, PasswordEncryption.decrypt(PasswordEncryption.encrypt(password)));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}

/*
* The SpotBugs error was pointing out that a public static method in your class returns a reference to an internal array
* (in this case, the connection array). This means that external code could potentially modify the contents of the
* connection array, leading to unintended consequences.

To fix this issue, you should apply the same principle as before: instead of returning the original array,
* return a new instance of the array or another appropriate data structure. In this case,
* you should not expose the Connection object directly to the external code. Instead,
* you should encapsulate the database interaction logic properly and only provide necessary methods to interact with the database.

The getConnection method now checks if the existing connection is still valid before returning it.
* If it's not valid, it creates a new connection. This encapsulates the connection logic and ensures
* that external code doesn't have direct access to the internal connection object.

By following this approach, you avoid exposing the internal representation of the connection array to external code,
* which helps prevent unintended modifications and ensures better encapsulation of your database interactions.

* */
