package com.yue.czcontrol;

import com.yue.czcontrol.error.IncompatibleVersionsError;
import com.yue.czcontrol.error.SocketConnectFailedError;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.window.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     * Stage.
     */
    private static Stage stage;
    /**
     * Get Stage.
     * @return Main.stage
     */
    public static Stage getStage() {
        return Main.stage;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(
                getClass().getResource("window/fxml/Splash.fxml"));
        stage.setResizable(false);
        stage.setTitle("Cz Control v3");
        stage.setScene(new Scene(root, 760, 553));
        stage.show();
        stage.setOnCloseRequest(e -> {
            e.consume();
            AlertBox.show("Close Window",
                    "Do you want close the window?",
                    AlertBox.Type.EXIT);
        });
        root.requestFocus();

        try {
            new SplashController();
        } catch (SocketConnectFailedError e) {
            AlertBox.show("Connect Failed",
                    "\u9023\u7dda\u903e\u6642",
                    AlertBox.Type.ERROR);
            System.exit(0);
        } catch (IncompatibleVersionsError e) {
            AlertBox.show("Disconnect From Server",
                    "\u7248\u672c\u904e\u6642\u6216\u4e0d"
                            + "\u7b26\u5408\u4f3a\u670d\u5668\u898f\u7bc4"
                    + ". Error Code:" + ErrorCode.IncompatibleVersions.getCode(),
                    AlertBox.Type.ERROR);
            System.exit(0);
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }

    /**
     * Main.
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
