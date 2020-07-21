package com.yue.czcontrol.error;

import com.yue.czcontrol.ErrorCode;

public class IncompatibleVersionsError extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the IncompatibleVersions.
     */
    public IncompatibleVersionsError() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the IncompatibleVersions.
     * @param msg The message
     */
    public IncompatibleVersionsError(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the IncompatibleVersions.
     * @param msg The message
     * @param cause Cause by
     */
    public IncompatibleVersionsError(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.IncompatibleVersions.getCode();
    }

}
