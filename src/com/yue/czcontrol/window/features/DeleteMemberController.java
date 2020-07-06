package com.yue.czcontrol.window.features;

import com.jfoenix.controls.JFXComboBox;
import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.exception.NameNotFoundException;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.BoxInit;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.StackTrace;
import com.yue.czcontrol.utils.TimeProperty;
import com.yue.czcontrol.window.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DeleteMemberController
        implements Initializable, BoxInit, TimeProperty, SocketSetting {
    /**
     * Date Formatter.
     */
    private final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_FORMAT);

    /**
     * PrintWriter.
     */
    private PrintWriter out;

    /**
     * Combo box.
     */
    @FXML
    private JFXComboBox<String> box;
    /**
     * If the member exist, return true. if not, return false.
     * @param id Member id
     * @return boolean
     */
    private boolean isExist(final String id) {
        try {
            //Get Data(SQL Syntax)
            String select = "SELECT * FROM player WHERE ID=?";

            //String select to PreparedStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, id);

            ResultSet rs = psst.executeQuery();

            //if get data return true
            //if not get return false
            return rs.next();

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox eb = new ExceptionBox(message);
            eb.show();
        } finally {
            DBConnector.close();
        }
        return false;
    }


    /**
     * Get the id member, if the member belong to handler, return true. If not return false.
     * @param id Member id
     * @param handler Member's handler
     * @return boolean
     */
    private boolean isHandler(final String id, final String handler) {
        try {
            //Get Data(SQL Syntax)
            String select = "SELECT * FROM player WHERE ID=? AND HANDLER=?";

            //String select to PreparedStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);

            psst.setString(1, id);
            psst.setString(2, handler);

            ResultSet rs = psst.executeQuery();

            //If get data return true
            //if not return false
            return rs.next();

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox eb = new ExceptionBox(message);
            eb.show();
        } finally {
            DBConnector.close();
        }
        return false;
    }

    /**
     * Delete member.
     * @param id member id
     * @param handler member's handler
     * @throws UploadFailedException When the data upload failed
     */
    private void deleteMember(final String id, final String handler)
            throws UploadFailedException {
        try {
            boolean isExistOK = isExist(id);
            boolean isHandlerOK = isHandler(id, handler);

            if (isExistOK) {
                if (isHandlerOK) {
                    //Delete data (SQL Syntax)
                    String delete = "DELETE FROM `player` WHERE ID=?";
                    //String delete to PreparedStatement
                    PreparedStatement psst =
                            DBConnector.getConnection()
                                    .prepareStatement(delete);
                    psst.setString(1, id);

                    if (psst.executeUpdate() > 0) { //if get update > 0
                        AlertBox.show("Complete",
                                "\u522a\u9664 \u7de8\u865f[" + id + "] "
                                        + "\u5df2\u6210\u529f",
                                AlertBox.Type.INFORMATION);
                        message(
                                DATE_FORMATTER.format(new Date())
                                        + "\t" + handler
                                        + "\u70ba\u7de8\u865f [" + id + "]"
                                        + "\u8acb\u5047 ~[console]");
                    } else {
                        throw new UploadFailedException(
                                "Data is Upload failed.");
                    }

                } else {
                    AlertBox.show("Warning",
                            "\u4f60\u4e0d\u662f\u6b64\u6210"
                                    + "\u54e1\u7684\u8ca0\u8cac\u4eba",
                            AlertBox.Type.WARNING);
                }
            } else {
                AlertBox.show("Warning",
                        "\u6c92\u6709\u6b64id\u6210\u54e1",
                        AlertBox.Type.WARNING);
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox eb = new ExceptionBox(message);
            eb.show();
        }
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
        } catch (IOException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        }
        initBox(box);
    }

    /**
     * Delete Member.
     */
    @FXML
    private void apply() {
        try {
            String[] item = box.getValue().split(":");
            String id = getID(item[1]);
            deleteMember(id, LoginController.getUserName());
        } catch (NameNotFoundException | UploadFailedException nne) {
            String message = StackTrace.getStackTrace(nne);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        }
    }

    /**
     * Set the ComboBox's item.
     *
     * @param box JComboBox
     */
    @Override
    public void initBox(final JFXComboBox<String> box) {
        try {
            String select = "SELECT * FROM PLAYER WHERE HANDLER=?";
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, LoginController.getUserName());

            ResultSet rs = psst.executeQuery();

            while (rs.next()) {
                box.getItems()
                        .add("Num."
                                + rs.getString("ID")
                                + ":"
                                + rs.getString("NAME"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox eb = new ExceptionBox(message);
            eb.show();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * Use member name to get ID.
     *
     * @param name member's name
     * @return String name
     * @throws NameNotFoundException When the NameNotFound
     */
    @Override
    public String getID(final String name) throws NameNotFoundException {
        try {
            String select = "SELECT `ID` FROM PLAYER WHERE NAME=?";

            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, name);

            ResultSet rs = psst.executeQuery();

            if (rs.next()) {
                return rs.getString("ID");
            } else {
                throw new NameNotFoundException(
                        "The name is can't get the user id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox eb = new ExceptionBox(message);
            eb.show();
        } finally {
            DBConnector.close();
        }
        return null;
    }

    /**
     * add Data to DataBase.
     *
     * @param msg msg
     * @throws UploadFailedException upload failed
     */
    @Override
    public void addData(final String msg) throws UploadFailedException {
    }

    /**
     * get data from database.
     */
    @Override
    public void initData() {
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
}
