package com.yue.czcontrol;

import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.utils.StackTrace;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public final class AlertBox {

    private AlertBox() {
    }

    /**
     * Type Enum.
     */
    public enum Type {
        /**
         * Information.
         */
        INFORMATION,
        /**
         * Warning.
         */
        WARNING,
        /**
         * Confirmation.
         */
        CONFIRMATION
    }

    /**
     * AlertBox.
     */
    private static Alert alert;

    /**
     * Show AlertBox.
     * @param title Alert Title
     * @param message Alert Msg
     * @param type Alert Type
     */
    public static void show(final String title, final String message,
                            final Type type){
        if (type == Type.INFORMATION) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            init(alert, title, message);
        } else if (type == Type.WARNING) {
            alert = new Alert(Alert.AlertType.WARNING);
            init(alert, title, message);
        } else if (type == Type.CONFIRMATION) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            init(alert, title, message);
            final ButtonType btn = alert.getResult();

            if (btn == ButtonType.OK) {
                try{
                    SocketConnector.getSocket().close();
                    DBConnector.close();
                } catch (IOException e){
                    String stack = StackTrace.getStackTrace(e);
                    new ExceptionBox(stack).show();
                }
                System.exit(0);
            }
        }
    }

    /**
     * Show Alert Box.
     * @param title Alert Title
     * @param header Alert Header
     * @param message Alert msg
     * @param type Alert type
     */
    public static void show(final String title, final String header,
                             final String message, final Type type) {
        if (type == Type.INFORMATION) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            init(alert, title, header, message);
        } else if (type == Type.WARNING) {
            alert = new Alert(Alert.AlertType.WARNING);
            init(alert, title, header, message);
        } else if (type == Type.CONFIRMATION) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            init(alert, title, header, message);
            final ButtonType btn = alert.getResult();

            if (btn == ButtonType.OK) {
                System.exit(0);
            }
        }
    }

    /**
     * Set Alert without Header.
     * @param alert Alert
     * @param title Title
     * @param message ContentMessage
     */
    private static void init(final Alert alert,
                             final String title, final String message) {
        alert.setTitle(title);
        alert.setHeaderText("");
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Set Alert with Header.
     * @param alert Alert
     * @param title Title
     * @param header HeaderText
     * @param message ContentMessage
     */
    private static void init(final Alert alert, final String title,
                             final String header, final String message) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
