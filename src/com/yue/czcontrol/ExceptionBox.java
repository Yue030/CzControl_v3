/**
 * CzControl Main Package.
 * @author Yue
 * @since 2020/6/25
 * @version 1
 */
package com.yue.czcontrol;

import com.yue.czcontrol.utils.StackTrace;
import com.yue.czcontrol.window.ExceptionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ExceptionBox {

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
     *
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
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        }
    }
}
