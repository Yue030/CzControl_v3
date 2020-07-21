package com.yue.czcontrol.window.features;

import com.jfoenix.controls.JFXTextField;
import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.AddDataNotCompletedException;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.StackTrace;
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

public class AddMemberController implements
        Initializable, SocketSetting, TimeProperty {

    /**
     * Name Input.
     */
    @FXML
    private JFXTextField nameInput;
    /**
     * Rank Input.
     */
    @FXML
    private JFXTextField rankInput;
    /**
     * Active Input.
     */
    @FXML
    private JFXTextField activeInput;
    /**
     * id Label.
     */
    @FXML
    private Label id;

    /**
     * DateFormatter.
     */
    private final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_FORMAT);
    /**
     * Prepared statement.
     */
    private PreparedStatement psst;

    /**
     * PrintWriter.
     */
    private PrintWriter out;

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

        try {
            id.setText("Next Member will be the ID [" + getNextID() + "]");
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        }

    }

    /**
     * add Data to DataBase.
     *
     * @param msg msg
     * @see SocketSetting
     */
    @Override
    public void addData(final String msg) {

    }

    /**
     * get data from database.
     * @see SocketSetting
     */
    @Override
    public void initData() {

    }

    /**
     * send Message to server.
     *
     * @param msg msg
     * @see SocketSetting
     */
    @Override
    public void message(final String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Get Next Id
     * @return id
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private int getNextID() throws DBCloseFailedError {
        int maxID = 0;
        try {
            String select = "SELECT `ID` FROM `PLAYER`";
            psst = DBConnector.getConnection().prepareStatement(select);

            ResultSet rs = psst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);

                if (id > maxID) {
                    maxID = id;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } finally {
            DBConnector.close();
        }

        return maxID + 1;
    }

    /**
     * Add a user data to DataBase without Active.
     *
     * @param name The user's name
     * @param rank The user's rank
     * @param handler Handler
     * @throws UploadFailedException When the data upload failed
     * @throws DBCloseFailedError DataBase Object Close Failed
     *
     */
    private void insertData(final String name,
                            final String rank, final String handler)
            throws UploadFailedException, DBCloseFailedError {
        try {
            //Add data(SQL Syntax)
            String insert =
                    "INSERT INTO `player`(`NAME`, `RANK`, `HANDLER`) "
                            + "VALUES (?, ?, ?)";

            //String insert to PreparedStatement
            psst = DBConnector.getConnection().prepareStatement(insert);
            psst.setString(1, name);
            psst.setString(2, rank);
            psst.setString(3, handler);

            //Detect the data is add or not
            if (psst.executeUpdate() > 0) {
                message(
                        DATE_FORMATTER.format(new Date())
                                + "\t" + handler
                                + "\u65b0\u589e [" + name + "]"
                                + "\u6210\u54e1 ~[console]");
                nameInput.setText("");
                rankInput.setText("");
                id.setText("Next Member will be the ID [" + getNextID() + "]");
                AlertBox.show("Complete",
                        "\u4f60\u5df2\u6210\u529f\u65b0"
                                + "\u589e\u4e00\u540d\u6210\u54e1",
                        AlertBox.Type.INFORMATION);
            } else {
                throw new UploadFailedException("Data is Upload failed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * Add a user data to DataBase with Active.
     *
     * @param name The user's name
     * @param rank The user's rank
     * @param active The user's active
     * @param handler Handler
     * @throws UploadFailedException When the data upload failed
     * @throws DBCloseFailedError DataBase Object Close Failed
     *
     */
    private void insertData(final String name, final String rank,
                            final String active, final String handler)
            throws UploadFailedException, DBCloseFailedError {
        try {
            //Add data(SQL Syntax)
            String insert =
                    "INSERT INTO `player`(`NAME`, `RANK`,`ACTIVE`, `HANDLER`) "
                            + "VALUES (?, ?, ?, ?)";

            //String insert to PreparedStatement
            psst = DBConnector.getConnection().prepareStatement(insert);
            psst.setString(1, name);
            psst.setString(2, rank);
            psst.setString(3, active);
            psst.setString(4, handler);

            //Detect the data is add or not
            if (psst.executeUpdate() > 0) {
                message(
                        DATE_FORMATTER.format(new Date())
                                + "\t" + handler
                                + "\u65b0\u589e [" + name + "]"
                                + "\u6210\u54e1 ~[console]");
                nameInput.setText("");
                rankInput.setText("");
                activeInput.setText("");
                id.setText("Next Member will be the ID [" + getNextID() + "]");
                AlertBox.show("Complete",
                        "\u4f60\u5df2\u6210\u529f\u65b0"
                                + "\u589e\u4e00\u540d\u6210\u54e1",
                        AlertBox.Type.INFORMATION);
            } else {
                throw new UploadFailedException("Data is Upload failed.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * When action button.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @FXML
    public void action() throws DBCloseFailedError {
        String name = nameInput.getText();
        String rank = rankInput.getText();
        String active = activeInput.getText().isEmpty()
                ? null : activeInput.getText();
        //Detect name and rank is empty. if not, continue method
        try {
            if (!name.isEmpty() && !rank.isEmpty()) {
                //Detect active is empty or not
                if (active == null) {
                    try {
                        insertData(name, rank, LoginController.getUserName());
                    } catch (UploadFailedException nne) {
                        String message = StackTrace.getStackTrace(nne);
                        ExceptionBox box = new ExceptionBox(message);
                        box.show();
                    } catch (DBCloseFailedError e){
                        ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                        box.show();
                    }
                } else {
                    try {
                        insertData(name, rank, active,
                                LoginController.getUserName());
                    } catch (UploadFailedException nne) {
                        String message = StackTrace.getStackTrace(nne);
                        ExceptionBox box = new ExceptionBox(message);
                        box.show();
                    } catch (DBCloseFailedError e) {
                        ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                        box.show();
                    } finally {
                        DBConnector.close();
                    }
                }
            } else {
                throw new AddDataNotCompletedException("Name or Rank is empty.");
            }
        } catch (AddDataNotCompletedException e) {
            AlertBox.show("Warning",
                    "\u8cc7\u6599\u4e0d\u5b8c\u6574",
                    AlertBox.Type.WARNING);
        } catch (Exception e) {
            throw new UnknownException();
        }
    }
}
