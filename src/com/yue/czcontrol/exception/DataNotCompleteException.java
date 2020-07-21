package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class DataNotCompleteException extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the DataNotCompleted.
     */
    public DataNotCompleteException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the DataNotCompleted.
     * @param msg The message
     */
    public DataNotCompleteException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the DataNotCompleted.
     * @param msg The message
     * @param cause Cause by
     */
    public DataNotCompleteException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.DataNotCompleted.getCode();
    }

}
