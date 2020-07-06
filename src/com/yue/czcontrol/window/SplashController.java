package com.yue.czcontrol.window;

import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.Main;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.VersionProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SplashController implements VersionProperty, SocketSetting {

    /**
     * PrintWriter.
     */
    private final PrintWriter out;
    /**
     * BufferedReader.
     */
    private final BufferedReader in;

    @Override
    public void addData(final String msg) throws UploadFailedException {
    }

    /**
     * Init data.
     * @see SocketSetting
     */
    @Override
    public void initData() {
    }

    /**
     * Send Message to server.
     * @param msg msg
     * @see SocketSetting
     */
    @Override
    public void message(final String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Get version.
     * @return "v2-" + version
     * @see VersionProperty
     */
    @Override
    public String getVersion() {
        final int product = 16;
        int version = ((DATE_YEAR * DATE_MD) / (RELEASE_TIME * RELEASE_COUNT))
                * (product + RELEASE_AM_PM);
        return "v2-" + version;
    }

    /**
     * Constructor.
     * @throws IOException IOException
     */
    public SplashController() throws IOException {

        SocketConnector.init();

        out = new PrintWriter(SocketConnector.getSocket().getOutputStream());
        in = new BufferedReader(
                new InputStreamReader(SocketConnector.getSocket().getInputStream()));

        if (in.readLine() != null) {
            message(getVersion() + " ~[version]");
            String versionOK = in.readLine();
            if (versionOK.equals("shutdown")) {
                AlertBox.show("Disconnect From Server",
                        "\u7248\u672c\u904e\u6642\u6216\u4e0d"
                        + "\u7b26\u5408\u4f3a\u670d\u5668\u898f\u7bc4",
                        AlertBox.Type.WARNING);
                System.exit(0);
            }

            loading();
        }
    }

    /**
     * Loading to Login Scene.
     * @throws IOException IOException
     */
    private void loading() throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("fxml/Login.fxml"));
        Main.getStage().setScene(new Scene(root, 1400, 700));
        root.requestFocus();
    }
}
