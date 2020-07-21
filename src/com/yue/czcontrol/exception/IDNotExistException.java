package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class IDNotExistException extends Exception {

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the IDNotExist.
     */
    public IDNotExistException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the IDNotExist.
     * @param msg The message
     */
    public IDNotExistException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the IDNotExist.
     * @param msg The message
     * @param cause Cause by
     */
    public IDNotExistException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.IDNotExist.getCode();
    }

}
