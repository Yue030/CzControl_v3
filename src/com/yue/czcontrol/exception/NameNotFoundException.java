package com.yue.czcontrol.exception;

import com.yue.czcontrol.ErrorCode;

public class NameNotFoundException extends Exception {

	/**
	 * serial Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * When throw a NameNotFound.
	 */
	public NameNotFoundException() {
		super("Error Code:" + getCode() + ". ");
	}
	/**
	 * When throw a NameNotFound.
	 * @param msg The message
	 */
	public NameNotFoundException(final String msg) {
		super("Error Code:" + getCode() + ". " + msg);
	}
	/**
	 * When throw a NameNotFound.
	 * @param msg The message
	 * @param cause Cause by
	 */
	public NameNotFoundException(final String msg, final Throwable cause) {
		super("Error Code:" + getCode() + ". " + msg, cause);
	}

	/**
	 * Get Error Code.
	 * @return error code
	 */
	public static int getCode(){
		return ErrorCode.NameNotFound.getCode();
	}
}
