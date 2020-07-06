package com.yue.czcontrol;

import com.yue.czcontrol.utils.StackTrace;
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
     * Start.
     * @param primaryStage stage
     * @throws Exception Exception
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
        stage.setOnCloseRequest(e-> {
            e.consume();
            AlertBox.show("Close Window",
                    "Do you want close the window?",
                    AlertBox.Type.CONFIRMATION);
        });
        root.requestFocus();
        new SplashController();
    }

    /**
     * Main.
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
