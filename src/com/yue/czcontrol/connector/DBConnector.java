/**
 * Connector.
 * @author Yue
 * @since 2020/6/25
 * @version 1
 */
package com.yue.czcontrol.connector;

import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.utils.StackTrace;

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
     */
    public static void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        }
    }

    /**
     * Get Connection.
     * @return conn
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public static Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            conn = DriverManager.getConnection(DATA_SOURCE);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertBox.show("Connect Failed",
                    "Can't connect to SQL DataBase",
                    AlertBox.Type.INFORMATION);
            System.exit(0);
        }
        return conn;
    }
}
