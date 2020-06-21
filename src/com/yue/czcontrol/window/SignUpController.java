package com.yue.czcontrol.window;

import com.yue.czcontrol.Main;
import com.yue.czcontrol.exception.UploadFailedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {
    public Button signInBtn;

    public TextField nameInput;
    public TextField accountInput;
    public TextField passwordInput;
    public TextField confirmPasswordInput;

    public Label error;

    private boolean isOK;

    /**
     * Detect account is exist or not
     * @param account Input account
     * @return boolean
     */
    private boolean isExistAccount(String account) {
        try {
            //Get Data(SQL Syntax)
            String accountList = "SELECT * FROM admin WHERE ACCOUNT=?";

            //String accountList to PreparedStatement
            PreparedStatement psst = SplashController.getConn().prepareStatement(accountList);
            psst.setString(1, account);

            ResultSet rs = psst.executeQuery();

            //Detect the data is exist or not
            return rs.next();

        } catch (SQLException e) {
            error.setText("\u8cc7\u6599\u5eab\u4e0d\u5b58\u5728");
        }
        return false;
    }


    /**
     * Register Account
     * @param name Input name
     * @param account Input account
     * @param password Input password
     * @param confirmPassword Input confirmPassword
     * @throws UploadFailedException When the data upload failed
     */

    private void register(String name, String account, String password, String confirmPassword) throws UploadFailedException {
        boolean accountExist = isExistAccount(account);

        if (accountExist) {
            error.setText("\u5e33\u865f\u5df2\u5b58\u5728");
        } else {
            //Detect the password and confirmPassword is equals or not
            if (password.equals(confirmPassword)) {
                try {
                    //Add data(SQL Syntax)
                    String insertAccount = "INSERT INTO `admin`(`NAME`, `ACCOUNT`, `PASSWORD`) VALUES (?,?,?)";

                    //String insertAccount to PreparedStatement
                    PreparedStatement psst = SplashController.getConn().prepareStatement(insertAccount);
                    psst.setString(1, name);
                    psst.setString(2, account);
                    psst.setString(3, password);

                    //Detect the data is upload success of not
                    if (psst.executeUpdate() > 0) {
                        isOK = true;
                    } else {
                        throw new UploadFailedException("Data is Upload failed.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                error.setText("\u5bc6\u78bc\u5fc5\u9808\u76f8\u540c");
            }
        }
    }

    public void signUp() throws IOException {
        isOK = false;
        //Get the input data
        String name = nameInput.getText();
        String account = accountInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        //Set String regex format
        final String regex = "[A-Za-z0-9]\\w{3,10}";
        if (!name.isEmpty() && !account.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {//Detect the input data is empty. if not, continue the method
            if (account.matches(regex) && password.matches(regex) && confirmPassword.matches(regex)) {//Detect the input data is meets the format
                try {
                    register(name, account, password, confirmPassword);
                } catch (UploadFailedException ufe) {
                    error.setText("\u8a3b\u518a\u5931\u6557");
                    ufe.printStackTrace();
                }//Register
            } else {
                error.setText("\u683c\u5f0f\u4e0d\u7b26\u5408(\u6700\u5c114,\u6700\u591a10\u5b57)");
            }
        } else {
            error.setText("\u8cc7\u6599\u4e0d\u5b8c\u6574");
        }

        if (isOK) {
            JOptionPane.showMessageDialog(null, "\u8a3b\u518a\u5e33\u865f\u6210\u529f");
            toSignIn();
        }
    }

    /**
     * Change to SignIn Scene
     * @throws IOException IOException
     */
    public void toSignIn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        Main.stage.setScene(new Scene(root, 1400, 700));
        root.requestFocus();
    }
}
