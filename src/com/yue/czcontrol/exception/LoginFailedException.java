package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class LoginFailedException extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the LoginFailed.
     */
    public LoginFailedException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the LoginFailed.
     * @param msg The message
     */
    public LoginFailedException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the LoginFailed.
     * @param msg The message
     * @param cause Cause by
     */
    public LoginFailedException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.LoginFailed.getCode();
    }
}
