package com.yue.czcontrol.window.features;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ErrorCode;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.LeaveDataNotCompletedException;
import com.yue.czcontrol.exception.NameNotFoundException;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.BoxInit;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.TimeProperty;
import com.yue.czcontrol.window.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AddLeaveController implements BoxInit, SocketSetting, TimeProperty, Initializable {

    /**
     * Reason.
     */
    @FXML
    private JFXTextField reason;
    /**
     * Date.
     */
    @FXML
    private JFXTextField date;
    /**
     * Handler.
     */
    @FXML
    private Label handler;
    /**
     * Member.
     */
    @FXML
    private JFXComboBox<String> member;

    /**
     * PrintWriter.
     */
    private PrintWriter out;
    /**
     * PreparedStatement.
     */
    private PreparedStatement psst = null;
    /**
     * ResultSet.
     */
    private ResultSet rs = null;
    /**
     *  DateFormat.
     */
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * detect the id is exist or not
     * @param id need detect id
     * @return boolean
     */
    private boolean detectExistID(String id) {
        try {
            //Get data(SQL Syntax)
            String select = "SELECT * FROM `player` WHERE ID=?";
            //String select to PreparedStatement
            psst = DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, id);

            rs = psst.executeQuery();

            return rs.next();

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (DBConnectFailedError e) {
            ExceptionBox b = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            b.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
        return false;
    }

    /**
     * Add data to DB
     *
     * @param lid The member id
     * @param reason The member why leave
     * @param date Leave Dates
     * @param handler The handler
     */
    private void addLeave(String lid, String reason, String date, String handler) {
        boolean isOK = detectExistID(lid);
        if(isOK) {
            try {
                //add data(SQL Syntax)
                String insert = "INSERT INTO `leave`(`LID`,`REASON`, `DATE`, `HANDLER`) VALUES (?, ?, ?, ?)";

                //String insert to PreparedStatement
                psst = DBConnector.getConnection().prepareStatement(insert);
                psst.setString(1, lid);
                psst.setString(2, reason);
                psst.setString(3, date);
                psst.setString(4, handler);

                if(psst.executeUpdate() > 0) {//if add success
                    AlertBox.show("Completed", "\u4f60\u5df2\u6210\u529f\u70ba\u7de8\u865f [" + lid + "] \u8acb\u5047", AlertBox.Type.INFORMATION);
                    message(dateFormatter.format(new Date())+ "\t" + LoginController.getUserName() + "\u70ba\u7de8\u865f [" + lid + "] \u8acb\u5047 ~[console]");
                } else {//if not
                    throw new UploadFailedException("Data is Upload failed.");
                }

            } catch(SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (DBCloseFailedError e) {
                ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                box.show();
            } catch (DBConnectFailedError e) {
                ExceptionBox b = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
                b.show();
            } catch (Exception e) {
                throw new UnknownException();
            }
        } else {
            AlertBox.show("Wrong", "\u6c92\u6709\u6b64id\u6210\u54e1", AlertBox.Type.WARNING);
        }
    }

    /**
     * Apply.
     */
    @FXML
    private void apply() {
        try {
            add();
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code:" + DBCloseFailedError.getCode());
            box.show();
        } catch (LeaveDataNotCompletedException e) {
            AlertBox.show("Wrong", "\u8cc7\u6599\u4e0d\u5b8c\u6574", AlertBox.Type.WARNING);
        }
    }

    /**
     * Add data.
     * @throws DBCloseFailedError DataBase close failed
     * @throws LeaveDataNotCompletedException Leave data not completed
     */
    private void add() throws DBCloseFailedError, LeaveDataNotCompletedException {
        String lid = getID(member.getValue());
        String date = this.date.getText();
        String reason = this.reason.getText();
        String handler = LoginController.getUserName();

        if(!lid.isEmpty() && !reason.isEmpty() && !date.isEmpty() && !handler.isEmpty()) {
            addLeave(lid, reason, date, handler);
        } else {
            throw new LeaveDataNotCompletedException("Leave Data is not completed");
        }
    }

    /**
     * Set the ComboBox's item.
     *
     * @param box JComboBox
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @Override
    public void initBox(JFXComboBox<String> box) throws DBCloseFailedError {
        try {
            String select = "SELECT `NAME` FROM PLAYER";

            psst = DBConnector.getConnection().prepareStatement(select);

            rs = psst.executeQuery();

            while(rs.next()) {
                box.getItems().add(rs.getString("NAME"));
            }
        } catch (DBConnectFailedError e) {
            ExceptionBox b = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            b.show();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * Use member name to get ID.
     *
     * @param name member's name
     * @return String name
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @Override
    public String getID(String name) throws DBCloseFailedError {
        try {
            String select = "SELECT `ID` FROM PLAYER WHERE NAME=?";
            psst = DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, name);

            rs = psst.executeQuery();

            if(rs.next()) {
                return rs.getString("ID");
            } else {
                throw new NameNotFoundException("The name is can't get the user id");
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (DBConnectFailedError e) {
            ExceptionBox b = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            b.show();
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
            DBConnector.close();
        }
        return null;
    }

    /**
     * add Data to DataBase.
     *
     * @param msg msg
     */
    @Override
    public void addData(String msg) {

    }

    /**
     * get data from database.
     *
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
    public void message(String msg) {
        out.println(msg);
        out.flush();
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
    public void initialize(URL location, ResourceBundle resources) {
        try {
            out = new PrintWriter(
                    SocketConnector.getSocket().getOutputStream());
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }

        try {
            handler.setText("Handler: " + LoginController.getUserName());
            initBox(member);
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }
}
