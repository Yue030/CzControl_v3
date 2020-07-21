package com.yue.czcontrol;

import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.window.ExceptionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;

public final class ExceptionBox {

    /**
     * Alert.
     */
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    /**
     * Constructor.
     * @param error Error message
     */
    public ExceptionBox(final String error) {
        ExceptionController.setText(error);
    }

    /**
     * show box
     */
    public void show() {
        try {
            Parent root = FXMLLoader.load(
                    getClass()
                            .getResource("window/fxml/ExceptionBox.fxml"));
            alert.setTitle("Get Exception");
            alert.setHeaderText("Exception is in here!");
            alert.getDialogPane().setContent(root);
            alert.showAndWait();
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }
}
