package com.yue.czcontrol.window;

import com.yue.czcontrol.Main;
import com.yue.czcontrol.exception.UploadFailedException;
import com.yue.czcontrol.utils.SocketSetting;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements SocketSetting, Initializable {

    public static String user;

    public Button signUpBtn;
    public Button signInBtn;

    public Label error;

    public TextField accountInput;
    public PasswordField passwordInput;
    public CheckBox remember;

    private Preferences preferences;

    private PrintWriter out;

    @Override
    public void addData(String msg) throws UploadFailedException {

    }

    @Override
    public void initData() {

    }

    @Override
    public void message(String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Make user can Login
     *
     * @param account User input account
     * @param password User input password
     *
     */
    private void login(String account, String password) throws IOException {

        try {
            String userList = "SELECT * FROM admin WHERE ACCOUNT= ? AND PASSWORD= ?";

            PreparedStatement psst = SplashController.getConn().prepareStatement(userList);
            psst.setString(1, account);
            psst.setString(2, password);

            ResultSet rs = psst.executeQuery();

            if(rs.next()) {
                String pin = JOptionPane.showInputDialog("\u8acb\u8f38\u5165\u9a57\u8b49\u78bc");
                if(pin.equals(rs.getString("PIN"))) {
                    message("\u5e33\u865f: " + account + ", \u5bc6\u78bc: " + password + " \u6210\u529f\u767b\u5165! \u5728 ~[console]");

                    user = account;

                    if(remember.isSelected()){
                        preferences.put("account", accountInput.getText());
                        preferences.put("password", passwordInput.getText());
                        preferences.put("remember", "true");
                    } else {
                        preferences.put("account", "");
                        preferences.put("password", "");
                        preferences.put("remember", "false");
                    }

                    Parent root = FXMLLoader.load(getClass().getResource("fxml/Menu.fxml"));
                    Main.stage.setScene(new Scene(root, 1000, 600));
                    root.requestFocus();
                } else {
                    error.setText("\u9a57\u8b49\u78bc\u932f\u8aa4");
                }

            } else {
                error.setText("\u6c92\u6709\u6b64\u5e33\u865f\u6216\u5bc6\u78bc\u8f38\u5165\u932f\u8aa4");
            }

        } catch (SQLException e) {
            error.setText("\u8cc7\u6599\u5eab\u4e0d\u5b58\u5728");
        }
    }

    /**
     * Change to other Scene
     * @param event ActionEvent
     * @throws IOException IOException
     */
    public void change(ActionEvent event) throws IOException {
        if(event.getSource() == signUpBtn){
            Parent root = FXMLLoader.load(getClass().getResource("fxml/SignUp.fxml"));
            Main.stage.setScene(new Scene(root, 1400, 700));
            root.requestFocus();
        } else if(event.getSource() == signInBtn){
            String account = accountInput.getText();
            String password = passwordInput.getText();
            login(account, password);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            out = new PrintWriter(SplashController.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        preferences = Preferences.userNodeForPackage(LoginController.class);
        if(preferences != null){
            if(preferences.get("account", null) != null){
                accountInput.setText(preferences.get("account", null));
            }

            if(preferences.get("password", null) != null){
                passwordInput.setText(preferences.get("password", null));
            }

            if(preferences.get("remember", null) != null){
                remember.setSelected(preferences.get("remember", null).equals("true"));
            }
        }
    }
}