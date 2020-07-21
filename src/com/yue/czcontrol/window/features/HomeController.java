package com.yue.czcontrol.window.features;

import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.MailSender;
import com.yue.czcontrol.TextInput;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.NameNotFoundException;
import com.yue.czcontrol.exception.UnknownException;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.listener.TimeListener;
import com.yue.czcontrol.utils.VersionProperty;
import com.yue.czcontrol.window.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements VersionProperty, Initializable {

    /**
     * Version.
     */
    @FXML
    private Label version;
    /**
     * User.
     */
    @FXML
    private Label user;
    /**
     * Time.
     */
    @FXML
    private Label time;

    /**
     * PreparedStatement.
     */
    private PreparedStatement psst;
    /**
     * ResultSet.
     */
    private ResultSet rs;

    /**
     * Get Version.
     * @return "v2-" + version
     * @see VersionProperty
     */
    @Override
    public String getVersion() {
        final int product = 16;
        int version = ((DATE_YEAR * DATE_MD) / (RELEASE_TIME * RELEASE_COUNT))
                * (product + RELEASE_AM_PM);
        return "v3-" + version;
    }

    /**
     * Init Label.
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private void initLabel() throws DBCloseFailedError {
        new TimeListener(time);
        version.setText("Version: " + getVersion());
        user.setText("User: " + LoginController.getUserName());
    }

    /**
     * Binding mail to account
     */
    @FXML
    private void bindMail() {
        String mail = TextInput.show("Binding Your Mail", "Please input your Gmail");
        try {
            addUserMail(LoginController.getUserAccount(), mail);
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        }
    }

    private void addUserMail(String account, String mail) throws DBCloseFailedError {
        try {
            String insert = "UPDATE `ADMIN` SET MAIL=? WHERE ACCOUNT=?";

            psst = DBConnector.getConnection().prepareStatement(insert);
            psst.setString(1, mail);
            psst.setString(2, account);

            if(psst.executeUpdate() > 0) {
                List<String> list = new ArrayList<>();
                list.add("Binding User: " + LoginController.getUserName());
                list.add("Binding Account: " + LoginController.getUserAccount());
                MailSender.send("CzControl Version 3", "Binding Gmail Success", mail, list);
            } else {
                throw new UploadFailedException("Mail data upload failed");
            }

        } catch (DBConnectFailedError dbConnectFailedError) {
            ExceptionBox b = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            b.show();
        } catch (UploadFailedException e) {
            ExceptionBox b = new ExceptionBox("Error Code: " + UploadFailedException.getCode());
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
     * get admin id using name.
     * @param name Name
     * @return rs.getString("ID");
     * @throws DBCloseFailedError DB close failed
     */
    private String getID(String name) throws DBCloseFailedError {
        try {
            String select = "SELECT `ID` FROM ADMIN WHERE ACCOUNT=?";
            psst = DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, name);

            rs = psst.executeQuery();

            if(rs.next()) {
                return rs.getString("ID");
            } else {
                throw new NameNotFoundException("The name is can't get the ADMIN id");
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
            initLabel();
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        } catch (Exception e) {
            throw new UnknownException();
        }
    }
}
