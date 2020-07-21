package com.yue.czcontrol.window.features;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.yue.czcontrol.ErrorCode;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.StackTrace;
import com.yue.czcontrol.utils.TimeProperty;
import com.yue.czcontrol.window.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MessageController implements
        Initializable, SocketSetting, TimeProperty, Runnable {
    /**
     * PrintWriter.
     */
    private PrintWriter out;
    /**
     * Get message.
     */
    private BufferedReader in;
    /**
     * Send Button.
     */
    @FXML
    private JFXButton button;
    /**
     * Message Input field.
     */
    @FXML
    private JFXTextField textField;
    /**
     * Message Area.
     */
    @FXML
    private JFXTextArea area;
    /**
     * Date Formatter.
     */
    private final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_FORMAT);

    /**
     * add Data to DataBase.
     *
     * @param msg msg
     */
    @Override
    public void addData(final String msg) throws DBCloseFailedError {
        try {
            String insert = "INSERT INTO `message`(`msg`) VALUES (?)";
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(insert);
            psst.setString(1, msg);

            if (psst.executeUpdate() <= 0) {
                throw new UploadFailedException("Data is Upload failed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            new ExceptionBox(message).show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * Clear the textField.
     */
    private void clear() {
        textField.setText("");
    }

    /**
     * Detect key, if ok, add data to DB.
     * @param keyCode key code
     * @param userName user name
     */
    private void addData_key(final KeyCode keyCode, final String userName) {
        if (keyCode == KeyCode.ENTER) {
            String msg = textField.getText();
            if (!msg.isEmpty()) {
                clear();
                String sendMsg = DATE_FORMATTER.format(new Date())
                        + "\t" + userName + ":" + msg;
                message(sendMsg);
                try {
                    addData(sendMsg);
                } catch (DBCloseFailedError e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                    box.show();
                } catch (Exception e) {
                    throw new UnknownException();
                }
            }
        }
    }
    /**
     * Button Click.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @FXML
    private void click() throws DBCloseFailedError {
        String msg = textField.getText();
        if (!msg.isEmpty()) {
            clear();
            String sendMsg = DATE_FORMATTER.format(new Date())
                    + "\t" + LoginController.getUserName() + ":" + msg;
            message(sendMsg);
            try {
                addData(sendMsg);
            } catch (DBCloseFailedError e) {
                ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                box.show();
            } catch (Exception e) {
                throw new UnknownException();
            }
        }
    }

    /**
     * get data from database.
     */
    @Override
    public void initData() throws DBCloseFailedError {
        try {
            String select = "SELECT * FROM `MESSAGE`";
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);

            ResultSet rs = psst.executeQuery();

            while (rs.next()) {
                area.appendText(
                        rs.getString("msg") + "\n");
            }
        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            new ExceptionBox(message).show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * send Message to server.
     *
     * @param msg msg
     */
    @Override
    public void message(final String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Get Event.
     * @param event Event
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @FXML
    private void getKey(final KeyEvent event) throws DBCloseFailedError {
        addData_key(event.getCode(), LoginController.getUserName());
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        try {
            out = new PrintWriter(
                    SocketConnector.getSocket().getOutputStream());
            in = new BufferedReader(
                    new InputStreamReader(
                            SocketConnector.getSocket().getInputStream()));
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
        try {
            initData();
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }

        new Thread(this).start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            while (true) {
                try {
                    String text = in.readLine();
                    if (!text.isEmpty()) {
                        area.appendText(text + "\n");
                    }
                } catch (NullPointerException | SocketException e) {
                    area.appendText("\u4f3a\u670d\u5668\u9023\u7dda\u65bc"
                            + "\u6642\u6216\u662f\u4f60\u5df2"
                            + "\u88ab\u5f37\u5236\u65b7"
                            + "\u7dda\uff0c\u8acb\u7a0d"
                            + "\u5f8c\u518d\u91cd\u958b"
                            + "\u6b64\u4ecb\u9762");
                    button.setDisable(true);
                    textField.setDisable(true);
                    break;
                }
            }
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }
}
