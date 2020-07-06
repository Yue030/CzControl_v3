package com.yue.czcontrol;

import com.yue.czcontrol.utils.StackTrace;
import javafx.scene.control.TextInputDialog;

import java.util.NoSuchElementException;

public final class TextInput {
    private TextInput() {
    }

    /**
     * Show TextInput Box and Return input String.
     * @param title Title
     * @param message Message
     * @return String
     */
    public static String show(final String title, final String message) {
        TextInputDialog input = new TextInputDialog();

        input.setTitle(title);
        input.setHeaderText("");
        input.setContentText(message);

        input.showAndWait();

        String result = null;
        try {
            result = input.getResult();
        } catch (NoSuchElementException e) {
            String text = StackTrace.getStackTrace(e);
            ExceptionBox box = new ExceptionBox(text);
            box.show();
        }

        return result;
    }

}
