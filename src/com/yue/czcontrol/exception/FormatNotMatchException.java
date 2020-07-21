package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class FormatNotMatchException extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the FormatNotMatch.
     */
    public FormatNotMatchException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the FormatNotMatch.
     * @param msg The message
     */
    public FormatNotMatchException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the FormatNotMatch.
     * @param msg The message
     * @param cause Cause by
     */
    public FormatNotMatchException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.FormatNotMatch.getCode();
    }

}
