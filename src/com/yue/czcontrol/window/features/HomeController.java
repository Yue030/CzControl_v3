package com.yue.czcontrol.window.features;

import com.yue.czcontrol.listener.TimeListener;
import com.yue.czcontrol.utils.VersionProperty;
import com.yue.czcontrol.window.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
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
     * Get Version.
     * @return "v2-" + version
     * @see VersionProperty
     */
    @Override
    public String getVersion() {
        final int product = 16;
        int version = ((DATE_YEAR * DATE_MD) / (RELEASE_TIME * RELEASE_COUNT))
                * (product + RELEASE_AM_PM);
        return "v2-" + version;
    }

    /**
     * Init Label.
     */
    private void initLabel() {
        new TimeListener(time);
        version.setText("Version: " + getVersion());
        user.setText("User: " + LoginController.getUserName());
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
        initLabel();
    }
}
