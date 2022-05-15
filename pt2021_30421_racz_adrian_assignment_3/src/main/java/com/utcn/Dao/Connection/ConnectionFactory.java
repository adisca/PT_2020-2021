package com.utcn.Dao.Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for managing database connection.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/ptdb";
    private static final String USER = "root";
    private static final String PASS = "";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a connection, using the url, username and password of the class.
     *
     * @return  The connection
     */
    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException sqlException) {
            LOGGER.log(Level.WARNING, "ERROR: Could not create a connection");
            sqlException.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes a connection.
     *
     * @param connection    The connection to be closed
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlException) {
                LOGGER.log(Level.WARNING, "ERROR: Could not close the connection");
                sqlException.printStackTrace();
            }
        }
    }

    /**
     * Closes a statement.
     *
     * @param statement The statement to be closed
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqlException) {
                LOGGER.log(Level.WARNING, "ERROR: Could not close the statement");
                sqlException.printStackTrace();
            }
        }
    }

    /**
     * Closes a resultSet.
     *
     * @param resultSet The resultSet to be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                LOGGER.log(Level.WARNING, "ERROR: Could not close the result set");
                sqlException.printStackTrace();
            }
        }
    }

}
