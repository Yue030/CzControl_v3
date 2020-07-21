package com.yue.czcontrol.connector;

import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnector {

    /**
     * Constructor.
     */
    private DBConnector() {

    }

    /**
     * Connection.
     */
    private static Connection conn;

    /**
     * Connection URL.
     */
    private static final String DATA_SOURCE =
            "jdbc:mysql://"
            + "27.147.3.116:3306/"
            + "cz_control?"
            + "user=TESTER"
            + "&password=tester&"
            + "serverTimezone=UTC";

    /**
     * Close the Connection.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    public static void close() throws DBCloseFailedError {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DBCloseFailedError("Close Failed.");
        }
    }

    /**
     * Get Connection.
     * @return conn
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws DBConnectFailedError DataBase connect failed
     */
    public static Connection getConnection() throws ClassNotFoundException, DBConnectFailedError {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            conn = DriverManager.getConnection(DATA_SOURCE);
        } catch (SQLException e) {
            throw new DBConnectFailedError("Connect Failed.");
        }
        return conn;
    }
}
