package com.yue.czcontrol.error;

import com.yue.czcontrol.ErrorCode;

public class SocketConnectFailedError extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the SocketConnectFailed.
     */
    public SocketConnectFailedError() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the SocketConnectFailed.
     * @param msg The message
     */
    public SocketConnectFailedError(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the SocketConnectFailed.
     * @param msg The message
     * @param cause Cause by
     */
    public SocketConnectFailedError(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode() {
        return ErrorCode.SocketConnectFailed.getCode();
    }

}
