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
import com.yue.czcontrol.exception.NameNotFoundException;
import com.yue.czcontrol.exception.NotExistMemberException;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.utils.BoxInit;
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

public class EditMemberController implements Initializable, TimeProperty, BoxInit, SocketSetting {
    /**
     * PrintWriter.
     */
    private PrintWriter out;

    /**
     * Member Choose Box.
     */
    @FXML
    private JFXComboBox<String> member;
    /**
     * Name Input.
     */
    @FXML
    private JFXTextField name;
    /**
     * Active Input.
     */
    @FXML
    private JFXTextField active;
    /**
     * Rank Input.
     */
    @FXML
    private JFXTextField rank;
    /**
     * Handler.
     */
    @FXML
    private Label handler;
    /**
     * Date Formatter
     */
    private final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Action Button.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @FXML
    private void apply() throws DBCloseFailedError {
        String[] value = member.getValue().split(":");
        String id = getID(value[1]);
        System.out.println(id);
        updateData(id,
                name.getText(),
                active.getText(),
                rank.getText());
    }

    /**
     * Refresh.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @FXML
    private void refresh() throws DBCloseFailedError {
        String[] value = member.getValue().split(":");
        String id = getID(value[1]);
        System.out.println(id);
        getData(id);
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
            out = new PrintWriter(SocketConnector.getSocket().getOutputStream());
        } catch (IOException e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        }
        try {
            initBox(member);
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }

    /**
     * Set the ComboBox's item.
     *
     * @param box JComboBox
     */
    @Override
    public void initBox(final JFXComboBox<String> box) throws DBCloseFailedError {
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
        } catch (DBConnectFailedError e) {
            ExceptionBox b = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            b.show();
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
     */
    @Override
    public String getID(final String name) throws DBCloseFailedError {
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
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
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
    public void addData(final String msg) {
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

    /**
     * get data from DataBase
     * and print it in TextField
     *
     * @param id The arg's id
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private void getData(final String id) throws DBCloseFailedError {
        try {
            //Get data(SQL Syntax)
            String select =
                    "SELECT * FROM PLAYER WHERE ID=?";

            //String select to PrepareStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, id);

            ResultSet rs = psst.executeQuery();

            //Detect data is exist
            if (rs.next()) {
                //Get the name and more data
                String name = rs.getString("NAME");
                String active = rs.getString("ACTIVE");
                String rank = rs.getString("RANK");
                String handler = rs.getString("HANDLER");

                //Write the data to input field
                this.name.setText(name);
                this.active.setText(active);
                this.rank.setText(rank);
                this.handler.setText("\u5275\u5efa\u8005: " + handler);
            } else {
                throw new NotExistMemberException();
            }

        } catch (ClassNotFoundException | SQLException e) {
            String message = StackTrace.getStackTrace(e);
            new ExceptionBox(message).show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } catch (NotExistMemberException e) {
            this.name.setText("");
            this.active.setText("");
            this.rank.setText("");
            this.handler.setText("\u5275\u5efa\u8005: ");
            AlertBox.show("Warning",
                    "\u7121\u6b64ID\u6210\u54e1" + " Error Code: " + NotExistMemberException.getCode(),
                    AlertBox.Type.WARNING);
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
          DBConnector.close();
        }
    }

    /**
     * Upload the data to dataBase
     *
     * @param id The user's id
     * @param name The user's name
     * @param active The user's active
     * @param rank The user's rank
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private void updateData(final String id, final String name, final String active, final String rank) throws DBCloseFailedError {
        try {
            //Edit the data(SQL Syntax)
            String update =
                    "UPDATE player SET NAME=?"
                            +", `RANK`=?"
                            +", ACTIVE=?"
                            + " WHERE ID=?";
            //String update to PrepareStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(update);

            psst.setString(1, name);
            psst.setString(2, rank);
            psst.setString(3, active);
            psst.setString(4, id);

            //Detect the data is update success or not
            if (psst.executeUpdate() > 0) {
                AlertBox.show("Complete",
                        "\u7de8\u8f2f \u7de8\u865f[" + id + "] \u5df2\u6210\u529f",
                        AlertBox.Type.INFORMATION);
                message(DATE_FORMATTER.format(new Date())
                        + "\t" + LoginController.getUserName()
                        + "\u5df2\u66f4\u6539 [" + id + "]"
                        + "\u6210\u54e1\u8cc7\u6599 ~[console]");
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
}
