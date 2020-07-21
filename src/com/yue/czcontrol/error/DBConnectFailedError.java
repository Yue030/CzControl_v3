package com.yue.czcontrol.error;

import com.yue.czcontrol.ErrorCode;

public class DBConnectFailedError extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the DBConnectFailed.
     */
    public DBConnectFailedError() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the DBConnectFailed.
     * @param msg The message
     */
    public DBConnectFailedError(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the DBConnectFailed.
     * @param msg The message
     * @param cause Cause by
     */
    public DBConnectFailedError(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.DBConnectFailed.getCode();
    }
}
