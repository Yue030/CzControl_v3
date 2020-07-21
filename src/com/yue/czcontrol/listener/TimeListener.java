package com.yue.czcontrol.listener;

import com.yue.czcontrol.utils.TimeProperty;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeListener {

    /**
     * Delay and Period.
     */
    private static final int DP = 1000;
    /**
     * Constructor.
     * @param label Label to set Text
     */
    public TimeListener(final Label label) {
        Timer timer = new Timer();
        timer.schedule(new TimeTask(label), DP, DP);
    }
}

class TimeTask extends TimerTask implements TimeProperty {
    /**
     * JLabel.
     */
    private final Label label;

    /**
     * DateFormat.
     */
    private final SimpleDateFormat dateFormatter =
            new SimpleDateFormat(DATE_FORMAT);

    /**
     * TimeTask.
     * @param arg Label
     */
    TimeTask(final Label arg) {
        this.label = arg;
    }

    /**
     * Set the format time to label.
     */
    @Override
    public void run() {
        Platform.runLater(() -> label.setText("Time: "
                + dateFormatter.format(new Date())));
    }
}
