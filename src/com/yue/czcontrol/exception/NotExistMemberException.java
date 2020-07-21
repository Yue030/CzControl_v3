package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class NotExistMemberException extends Exception {

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the NotExistMember.
     */
    public NotExistMemberException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the NotExistMember.
     * @param msg The message
     */
    public NotExistMemberException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the NotExistMember.
     * @param msg The message
     * @param cause Cause by
     */
    public NotExistMemberException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.NotExistMember.getCode();
    }

}
