package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class HandlerIsWrongException extends Exception {

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When throw a HandlerIsWrong.
     */
    public HandlerIsWrongException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When throw a HandlerIsWrong.
     * @param msg The message
     */
    public HandlerIsWrongException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When throw a HandlerIsWrong.
     * @param msg The message
     * @param cause Cause by
     */
    public HandlerIsWrongException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.HandlerIsWrong.getCode();
    }


}
