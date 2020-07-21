package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class PinIsWrongException extends Exception{
    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the PinIsWrong.
     */
    public PinIsWrongException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the PinIsWrong.
     * @param msg The message
     */
    public PinIsWrongException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the PinIsWrong.
     * @param msg The message
     * @param cause Cause by
     */
    public PinIsWrongException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.PinIsWrong.getCode();
    }
}
