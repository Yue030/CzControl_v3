package com.yue.czcontrol.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StackTrace {
    private StackTrace() {
    }

    /**
     * Throwable to String.
     * @param throwable Throwable
     * @return sw.getBuffer().toString()
     */
    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
