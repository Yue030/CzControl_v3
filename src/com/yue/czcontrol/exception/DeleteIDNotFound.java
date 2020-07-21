package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class DeleteIDNotFound extends Exception {

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When throw a DeleteIDNotFound.
     */
    public DeleteIDNotFound() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When throw a DeleteIDNotFound.
     * @param msg The message
     */
    public DeleteIDNotFound(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When throw a DeleteIDNotFound.
     * @param msg The message
     * @param cause Cause by
     */
    public DeleteIDNotFound(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.DeleteIDNotFound.getCode();
    }


}
