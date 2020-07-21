package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class LeaveDataNotCompletedException extends Exception {

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the LeaveDataNotCompleted.
     */
    public LeaveDataNotCompletedException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the LeaveDataNotCompleted.
     * @param msg The message
     */
    public LeaveDataNotCompletedException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the LeaveDataNotCompleted.
     * @param msg The message
     * @param cause Cause by
     */
    public LeaveDataNotCompletedException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.LeaveDataNotCompleted.getCode();
    }

}
