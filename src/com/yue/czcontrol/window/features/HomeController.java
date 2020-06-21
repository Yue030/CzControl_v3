package com.yue.czcontrol.window.features;

import com.yue.czcontrol.listener.TimeListener;
import com.yue.czcontrol.utils.VersionProperty;
import com.yue.czcontrol.window.LoginController;
import com.yue.czcontrol.window.SplashController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements VersionProperty, Initializable {

    public Label version;
    public Label user;
    public Label time;

    private String userName;

    @Override
    public String getVersion() {
        int version = ((DATE_YEAR * DATE_MD) / (RELEASE_TIME * RELEASE_COUNT)) * (16 + RELEASE_AM_PM);
        return "v2-" + version;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String account = LoginController.user;
        find(account);

        initLabel();
    }

    private void initLabel(){
        new TimeListener(time);
        version.setText("Version: " + getVersion());
        user.setText("User: " + userName);
    }

    private void find(String account){
        try{
            String select = "SELECT `NAME` FROM `ADMIN` WHERE ACCOUNT=?";
            PreparedStatement psst = SplashController.getConn().prepareStatement(select);
            psst.setString(1, account);

            ResultSet rs = psst.executeQuery();

            if(rs.next()){
                userName = rs.getString("NAME");
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
