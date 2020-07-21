package com.yue.czcontrol.window;

import com.yue.czcontrol.Main;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.IncompatibleVersionsError;
import com.yue.czcontrol.error.SocketConnectFailedError;
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

    @Override
    public void addData(final String msg) {
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
     * @return "v3-" + version
     * @see VersionProperty
     */
    @Override
    public String getVersion() {
        final int product = 16;
        int version = ((DATE_YEAR * DATE_MD) / (RELEASE_TIME * RELEASE_COUNT))
                * (product + RELEASE_AM_PM);
        return "v3-" + version;
    }

    /**
     * Constructor.
     * @throws IOException IOException
     * @throws IncompatibleVersionsError version is incompatible
     * @throws SocketConnectFailedError connect failed
     */
    public SplashController() throws IOException, IncompatibleVersionsError, SocketConnectFailedError {

        SocketConnector.init();

        out = new PrintWriter(SocketConnector.getSocket().getOutputStream());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(SocketConnector.getSocket().getInputStream()));

        System.out.println("System Version: " + getVersion());

        if (in.readLine() != null) {
            message(getVersion() + " ~[version]");
            String versionOK = in.readLine();
            if (versionOK.equals("shutdown")) {
                throw new IncompatibleVersionsError("Client is using incompatible version.");
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
