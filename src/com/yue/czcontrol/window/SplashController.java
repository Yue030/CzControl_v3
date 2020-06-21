package com.yue.czcontrol.window;

import com.yue.czcontrol.Main;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.VersionProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SplashController implements VersionProperty, SocketSetting {

    private static Socket socket;

    private static Connection conn;

    private static final String DATA_SOURCE = "jdbc:mysql://27.147.3.116:3306/cz_control?user=TESTER&password=tester&serverTimezone=UTC";

    private final PrintWriter out;
    private final BufferedReader in;

    public static Socket getSocket(){
        return socket;
    }

    public static Connection getConn(){
        return conn;
    }

    @Override
    public void addData(String msg) throws UploadFailedException {
    }

    @Override
    public void initData() {
    }

    @Override
    public void message(String msg) {
        out.println(msg);
        out.flush();
    }

    @Override
    public String getVersion() {
        int version = ((DATE_YEAR * DATE_MD) / (RELEASE_TIME * RELEASE_COUNT)) * (16 + RELEASE_AM_PM);
        return "v2-" + version;
    }


    private void initDB() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        try{
            conn = DriverManager.getConnection(DATA_SOURCE);
        } catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Can't connect to SQL DataBase");
            System.exit(0);
        }
    }

    public SplashController() throws IOException, ClassNotFoundException {
        try{
            SplashController.socket = new Socket("27.147.3.116", 5200);
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "\u9023\u7dda\u903e\u6642");
            e.printStackTrace();
        }

        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (in.readLine() != null) {
            message(getVersion() + " ~[version]");
            String versionOK = in.readLine();
            if (versionOK.equals("shutdown")) {
                JOptionPane.showMessageDialog(null, "\u7248\u672c\u904e\u6642\u6216\u4e0d\u7b26\u5408\u4f3a\u670d\u5668\u898f\u7bc4");
                System.exit(0);
            }
            initDB();

            loading();
        }
    }

    private void loading() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        Main.stage.setScene(new Scene(root, 1400, 700));
        root.requestFocus();
    }
}
