package com.yue.czcontrol;

import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.window.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
         * Exit.
         */
        EXIT,
        /**
         * Logout.
         */
        LOGOUT,
        /**
         * Error.
         */
        ERROR,
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
                            final Type type) {
        if (type == Type.INFORMATION) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            init(alert, title, message);
        } else if (type == Type.WARNING) {
            alert = new Alert(Alert.AlertType.WARNING);
            init(alert, title, message);
        } else if (type == Type.EXIT) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            init(alert, title, message);
            final ButtonType btn = alert.getResult();

            if (btn == ButtonType.OK) {
                try {
                    if(SocketConnector.getSocket() != null)
                        SocketConnector.getSocket().close();
                    if(DBConnector.getConnection() != null)
                        DBConnector.close();
                } catch (IOException e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
                    box.show();
                } catch (DBCloseFailedError e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                    box.show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (DBConnectFailedError e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
                    box.show();
                }
                System.exit(0);
            }
        } else if (type == Type.LOGOUT) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            init(alert, title, message);
            final ButtonType btn = alert.getResult();

            if (btn == ButtonType.OK) {
                LoginController.getUserPreferences().put("user", "null");
                try{
                    Parent root = FXMLLoader.load(
                            AlertBox.class.getResource("window/fxml/Login.fxml"));
                    Main.getStage().setScene(new Scene(root, 1400, 700));
                    root.requestFocus();
                } catch (IOException e){
                    ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
                    box.show();
                }
            }
        } else if (type == Type.ERROR) {
            alert = new Alert(Alert.AlertType.ERROR);
            init(alert, title, message);
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
        } else if (type == Type.EXIT) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            init(alert, title, message);
            final ButtonType btn = alert.getResult();

            if (btn == ButtonType.OK) {
                try {
                    if(SocketConnector.getSocket() != null)
                        SocketConnector.getSocket().close();
                    if(DBConnector.getConnection() != null)
                        DBConnector.close();
                } catch (IOException e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
                    box.show();
                } catch (DBCloseFailedError e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                    box.show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (DBConnectFailedError e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
                    box.show();
                }
                System.exit(0);
            }
        } else if (type == Type.LOGOUT) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            init(alert, title, header, message);
            final ButtonType btn = alert.getResult();

            if (btn == ButtonType.OK) {
                LoginController.getUserPreferences().put("user", "null");
                try{
                    Parent root = FXMLLoader.load(
                            AlertBox.class.getResource("window/fxml/Login.fxml"));
                    Main.getStage().setScene(new Scene(root, 1400, 700));
                    root.requestFocus();
                } catch (IOException e){
                    ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
                    box.show();
                }
            }
        } else if (type == Type.ERROR) {
            alert = new Alert(Alert.AlertType.ERROR);
            init(alert, title, header, message);
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
