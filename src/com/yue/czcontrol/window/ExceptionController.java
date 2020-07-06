package com.yue.czcontrol.window;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ExceptionController implements Initializable {
    /**
     * Label.
     */
    @FXML
    private Label label;

    /**
     * Text.
     */
    private static String text;

    /**
     * Set Text.
     * @param text Text
     */
    public static void setText(final String text) {
        ExceptionController.text = text;
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
        label.setText(text);
    }
}
