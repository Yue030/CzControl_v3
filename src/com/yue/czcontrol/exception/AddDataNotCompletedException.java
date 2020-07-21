package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class AddDataNotCompletedException extends Exception {

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the AddDataNotCompleted.
     */
    public AddDataNotCompletedException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the AddDataNotCompleted.
     * @param msg The message
     */
    public AddDataNotCompletedException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the AddDataNotCompleted.
     * @param msg The message
     * @param cause Cause by
     */
    public AddDataNotCompletedException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.AddDataNotCompleted.getCode();
    }

}
