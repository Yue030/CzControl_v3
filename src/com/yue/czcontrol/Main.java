package com.yue.czcontrol;

import com.yue.czcontrol.window.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("window/fxml/Splash.fxml"));
        stage.setResizable(false);
        stage.setTitle("Cz Control v3");
        stage.setScene(new Scene(root, 760, 553));
        stage.show();
        stage.setOnCloseRequest(e-> System.exit(0));
        root.requestFocus();
        new SplashController();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
