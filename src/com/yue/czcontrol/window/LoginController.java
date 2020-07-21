package com.yue.czcontrol.window;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.yue.czcontrol.*;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.connector.SocketConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.*;
import com.yue.czcontrol.utils.SocketSetting;
import com.yue.czcontrol.utils.StackTrace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements SocketSetting, Initializable {

    /**
     * To Sign Up Scene.
     */
    @FXML
    private Button signUpBtn;
    /**
     * Sign In.
     */
    @FXML
    private Button signInBtn;
    /**
     * Error Message.
     */
    @FXML
    private Label error;
    /**
     * AccountInput.
     */
    @FXML
    private JFXTextField accountInput;
    /**
     * Password Input.
     */
    @FXML
    private JFXPasswordField passwordInput;
    /**
     * Remember me CheckBox.
     */
    @FXML
    private JFXCheckBox remember;

    /**
     * Main.class Preferences.
     */
    private static final Preferences userAccount =
            Preferences.userNodeForPackage(Main.class);

    /**
     * Preferences of LoginController.class.
     */
    private Preferences preferences;
    /**
     * PrintWriter.
     */
    private PrintWriter out;

    /**
     * Get User Preferences.
     * @return LoginController.userAccount
     */
    public static Preferences getUserPreferences() {
        return LoginController.userAccount;
    }

    /**
     * Get UserAccount.
     * @return userAccount.get("user", null)
     */
    public static String getUserAccount() {
        return LoginController.userAccount.get("user", null);
    }

    /**
     * Get user Name.
     * @return {@link #find(String)}
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    public static String getUserName() throws DBCloseFailedError {
        return find(getUserAccount());
    }

    /**
     * add Data.
     * @param msg msg
     * @see SocketSetting
     */
    @Override
    public void addData(final String msg) {

    }

    /**
     * Init Data.
     * @see SocketSetting
     */
    @Override
    public void initData() {

    }

    /**
     * Send msg to server.
     * @param msg msg
     * @see SocketSetting
     */
    @Override
    public void message(final String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Get the name from account.
     * @param account User Account
     * @return rs.getString("NAME")
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private static String find(final String account) throws DBCloseFailedError {
        try {
            String select = "SELECT `NAME` FROM `ADMIN` WHERE ACCOUNT=?";
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, account);

            ResultSet rs = psst.executeQuery();

            if (rs.next()) {
                return rs.getString("NAME");
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
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
     * Make user can Login.
     *
     * @param account User input account
     * @param password User input password
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private void login(final String account,
                       final String password) throws DBCloseFailedError {

        try {
            String userList =
                    "SELECT * FROM admin WHERE ACCOUNT= ? AND PASSWORD= ?";

            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(userList);
            psst.setString(1, account);
            psst.setString(2, password);

            ResultSet rs = psst.executeQuery();

            if (rs.next()) {
                String pin =
                        TextInput.show("Push Pin",
                                "\u8acb\u8f38\u5165\u9a57\u8b49\u78bc");
                if (pin != null) {
                    if (pin.equals(rs.getString("PIN"))) {
                        message("\u5e33\u865f: "
                                + account
                                + ", \u5bc6\u78bc: "
                                + password
                                + " \u6210\u529f\u767b\u5165! \u5728 "
                                + "~[console]");

                        userAccount.put("user", account);

                        if (remember.isSelected()) {
                            preferences.put("account",
                                    accountInput.getText());
                            preferences.put("password",
                                    passwordInput.getText());
                            preferences.put("remember",
                                    "true");
                        } else {
                            preferences.put("account", "");
                            preferences.put("password", "");
                            preferences.put("remember", "false");
                        }

                        Parent root = FXMLLoader.load(
                                getClass().getResource("fxml/Menu.fxml"));
                        Main.getStage().setScene(new Scene(root, 1000, 600));
                        root.requestFocus();
                    } else {
                        throw new PinIsWrongException("Client is key-in a wrong pin.");
                    }
                }
            } else {
                throw new LoginFailedException("Client is key-in a not exist account or wrong password.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            error.setText("\u8cc7\u6599\u5eab\u4e0d\u5b58\u5728");

            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
        } catch (DBConnectFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBConnectFailedError.getCode());
            box.show();
        } catch (PinIsWrongException e) {
            error.setText("\u9a57\u8b49\u78bc\u932f\u8aa4");
            String message = StackTrace.getStackTrace(e);
            System.err.println(message);
        } catch (LoginFailedException e) {
            error.setText("\u6c92\u6709\u6b64\u5e33\u865f\u6216"
                    + "\u5bc6\u78bc\u8f38\u5165\u932f\u8aa4");
            String message = StackTrace.getStackTrace(e);
            System.err.println(message);
        } catch (Exception e) {
            throw new UnknownException();
        } finally {
            DBConnector.close();
        }
    }

    /**
     * Change to other Scene.
     * @param event ActionEvent
     * @throws IOException IOException
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    @FXML
    public void change(final ActionEvent event) throws IOException, DBCloseFailedError {
        if (event.getSource() == signUpBtn) {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/SignUp.fxml"));
            Main.getStage().setScene(new Scene(root, 1400, 700));
            root.requestFocus();
        } else if (event.getSource() == signInBtn) {
            String account = accountInput.getText();
            String password = passwordInput.getText();
            login(account, password);
        }
    }

    /**
     * Forget Password.
     */
    @FXML
    private void forgetPassword()  {
        String user = TextInput.show("Forget Password", "Please input your account");
        try {
            findPassword(user);
        } catch (DBCloseFailedError e) {
            ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
            box.show();
        }
    }

    private void findPassword(String user) throws DBCloseFailedError {
        try {
            String select = "SELECT * FROM ADMIN WHERE ACCOUNT=?";

            PreparedStatement psst = DBConnector.getConnection().prepareStatement(select);
            psst.setString(1, user);

            ResultSet rs = psst.executeQuery();

            if(rs.next()) {
                String name = rs.getString(2);
                String account = rs.getString(3);
                String password = rs.getString(4);
                String pin = rs.getString(5);
                String mail = rs.getString(7);

                List<String> list = new ArrayList<>();
                list.add("Name: " + name);
                list.add("Account: " + account);
                list.add("Password: " + password);
                list.add("Pin: " + pin);
                list.add("Mail: " + mail);

                MailSender.send("Cz Control Version 3", "Forget Password", mail, list);
            }

        } catch (SQLException | ClassNotFoundException e) {
            String message = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(message);
            box.show();
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
            ExceptionBox box = new ExceptionBox("Error Code: " + ErrorCode.IO.getCode());
            box.show();
        }

        preferences = Preferences.userNodeForPackage(LoginController.class);
        if (preferences != null) {
            if (preferences.get("account", null) != null) {
                accountInput.setText(preferences.get("account", null));
            }

            if (preferences.get("password", null) != null) {
                passwordInput.setText(preferences.get("password", null));
            }

            if (preferences.get("remember", null) != null) {
                remember.setSelected(
                        preferences.get("remember", null)
                                .equals("true"));
            }
        }
    }
}
