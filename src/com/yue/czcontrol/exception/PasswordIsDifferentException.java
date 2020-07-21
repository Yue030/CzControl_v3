package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class PasswordIsDifferentException extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the PasswordIsDifferent.
     */
    public PasswordIsDifferentException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the PasswordIsDifferent.
     * @param msg The message
     */
    public PasswordIsDifferentException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the PasswordIsDifferent.
     * @param msg The message
     * @param cause Cause by
     */
    public PasswordIsDifferentException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.PasswordIsDifferent.getCode();
    }

}
