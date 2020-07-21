package com.yue.czcontrol.error;

import com.yue.czcontrol.ErrorCode;

public class DBCloseFailedError extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the DBCloseFailed.
     */
    public DBCloseFailedError() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the DBCloseFailed.
     * @param msg The message
     */
    public DBCloseFailedError(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the DBCloseFailed.
     * @param msg The message
     * @param cause Cause by
     */
    public DBCloseFailedError(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.DBCloseFailed.getCode();
    }

}
