package com.yue.czcontrol.listener;

import com.yue.czcontrol.utils.TimeProperty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeListener {

    /**
     * Constuctor
     * @param label Label to set Text
     */
    public TimeListener(Label label){
        Timer timer = new Timer();
        timer.schedule(new TimeTask(label), 1000, 1000);
    }

}

class TimeTask extends TimerTask implements TimeProperty {
    /**
     * JLabel
     */
    private final Label label;

    /**
     * DateFormat
     */
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    /**
     * TimeTask
     * @param label JLabel
     */
    public TimeTask(Label label){
        this.label = label;
    }

    /**
     * Set the format time to label
     */
    @Override
    public void run() {
        Platform.runLater(() -> label.setText( "Time: " + dateFormatter.format(new Date())));
    }
}
