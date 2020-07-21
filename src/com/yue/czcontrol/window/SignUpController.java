package com.yue.czcontrol.window;

import com.jfoenix.controls.JFXTextField;
import com.yue.czcontrol.AlertBox;
import com.yue.czcontrol.ExceptionBox;
import com.yue.czcontrol.Main;
import com.yue.czcontrol.connector.DBConnector;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.error.DBConnectFailedError;
import com.yue.czcontrol.exception.*;
import com.yue.czcontrol.utils.StackTrace;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpController {
    /**
     * Name Input.
     */
    @FXML
    private JFXTextField nameInput;
    /**
     * Account Input.
     */
    @FXML
    private JFXTextField accountInput;
    /**
     * Password Input.
     */
    @FXML
    private JFXTextField passwordInput;
    /**
     * Confirm Password Input.
     */
    @FXML
    private JFXTextField confirmPasswordInput;
    /**
     * Error Message.
     */
    @FXML
    private Label error;

    /**
     * Is Ok to sign In.
     */
    private boolean isOK;

    /**
     * Detect account is exist or not.
     * @param account Input account
     * @return boolean
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    private boolean isExistAccount(final String account) throws DBCloseFailedError {
        try {
            //Get Data(SQL Syntax)
            String accountList = "SELECT * FROM admin WHERE ACCOUNT=?";

            //String accountList to PreparedStatement
            PreparedStatement psst =
                    DBConnector.getConnection().prepareStatement(accountList);
            psst.setString(1, account);

            ResultSet rs = psst.executeQuery();

            //Detect the data is exist or not
            return rs.next();

        } catch (SQLException | ClassNotFoundException e) {
            error.setText("\u8cc7\u6599\u5eab\u4e0d\u5b58\u5728");

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
        return false;
    }


    /**
     * Register Account.
     * @param name Input name
     * @param account Input account
     * @param password Input password
     * @param confirmPassword Input confirmPassword
     *
     * @throws DBCloseFailedError DataBase Object Close Failed
     * @throws AccountIsExistException Account Exist
     * @throws PasswordIsDifferentException Password is different
     */

    private void register(final String name, final String account,
                          final String password, final String confirmPassword)
            throws DBCloseFailedError, AccountIsExistException, PasswordIsDifferentException {
        boolean accountExist = isExistAccount(account);

        if (accountExist) {
            throw new AccountIsExistException("Account is exist");
        } else {
            //Detect the password and confirmPassword is equals or not
            if (password.equals(confirmPassword)) {
                try {
                    //Add data(SQL Syntax)
                    String insertAccount =
                            "INSERT INTO `"
                                    + "admin`(`NAME`, `ACCOUNT`, `PASSWORD`) "
                                    + "VALUES (?,?,?)";

                    //String insertAccount to PreparedStatement
                    PreparedStatement psst =
                            DBConnector.getConnection()
                                    .prepareStatement(insertAccount);
                    psst.setString(1, name);
                    psst.setString(2, account);
                    psst.setString(3, password);

                    //Detect the data is upload success of not
                    if (psst.executeUpdate() > 0) {
                        isOK = true;
                    } else {
                        throw new UploadFailedException(
                                "Data is Upload failed.");
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
            } else {
                throw new PasswordIsDifferentException("The password must be same.");
            }
        }
    }

    /**
     * SignUp.
     * @throws IOException IOException
     * @throws FormatNotMatchException Input format not match
     * @throws DataNotCompleteException Data not completed
     */
    @FXML
    public void signUp() throws IOException, FormatNotMatchException, DataNotCompleteException {
        isOK = false;
        //Get the input data
        String name = nameInput.getText();
        String account = accountInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();

        //Set String regex format
        final String regex = "[A-Za-z0-9]\\w{3,10}";
        if (!name.isEmpty()
                && !account.isEmpty()
                && !password.isEmpty()
                && !confirmPassword.isEmpty()) {
            //Detect the input data is empty. if not, continue the method
            if (account.matches(regex)
                    && password.matches(regex)
                    && confirmPassword.matches(regex)) {
                //Detect the input data is meets the format
                try {
                    register(name, account, password, confirmPassword);
                } catch (DBCloseFailedError e) {
                    ExceptionBox box = new ExceptionBox("Error Code: " + DBCloseFailedError.getCode());
                    box.show();
                } catch (AccountIsExistException e) {
                    error.setText("\u5e33\u865f\u5df2\u5b58\u5728");
                } catch (PasswordIsDifferentException e) {
                    error.setText("\u5bc6\u78bc\u5fc5\u9808\u76f8\u540c");
                } catch (Exception e) {
                    throw new UnknownException();
                }
            } else {
                error.setText("\u683c\u5f0f\u4e0d\u7b26\u5408(\u6700\u5c114"
                        + ",\u6700\u591a10\u5b57)");
                throw new FormatNotMatchException("Format Not match.");
            }
        } else {
            error.setText("\u8cc7\u6599\u4e0d\u5b8c\u6574");
            throw new DataNotCompleteException("Data Not Completed");
        }

        if (isOK) {
            AlertBox.show("Completed",
                    "\u8a3b\u518a\u5e33\u865f\u6210\u529f",
                    AlertBox.Type.INFORMATION);
            toSignIn();
        }
    }

    /**
     * Change to SignIn Scene.
     * @throws IOException IOException
     */
    @FXML
    public void toSignIn() throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("fxml/Login.fxml"));
        Main.getStage().setScene(new Scene(root, 1400, 700));
        root.requestFocus();
    }
}
