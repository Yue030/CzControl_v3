package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class AccountIsExistException extends Exception{

    /**
     * serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * When the AccountIsExist.
     */
    public AccountIsExistException() {
        super("Error Code:" + getCode() + ". ");
    }
    /**
     * When the AccountExist.
     * @param msg The message
     */
    public AccountIsExistException(final String msg) {
        super("Error Code:" + getCode() + ". " + msg);
    }
    /**
     * When the AccountIsExist.
     * @param msg The message
     * @param cause Cause by
     */
    public AccountIsExistException(final String msg, final Throwable cause) {
        super("Error Code:" + getCode() + ". " + msg, cause);
    }

    /**
     * Get Error Code.
     * @return error code
     */
    public static int getCode(){
        return ErrorCode.AccountIsExist.getCode();
    }

}
