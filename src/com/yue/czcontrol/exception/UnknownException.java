package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;
import com.yue.czcontrol.ExceptionBox;

public class UnknownException extends RuntimeException{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When throw a Unknown Exception.
     */
    public UnknownException() {
        super("Error Code:" + getCode() + ". ");
        ExceptionBox box = new ExceptionBox("Error Code: " + getCode());
        box.show();
    }
    /**
     * When throw a Unknown Exception.
     * @param msg The message
     */
    public UnknownException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
        ExceptionBox box = new ExceptionBox("Error Code: " + getCode());
        box.show();
    }
    /**
     * When throw a Unknown Exception.
     * @param msg The message
     * @param cause Cause by
     */
    public UnknownException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
        ExceptionBox box = new ExceptionBox("Error Code: " + getCode());
        box.show();
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.Unknown.getCode();
    }

}
