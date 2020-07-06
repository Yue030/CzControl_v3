package com.yue.czcontrol.exception;

public class NameNotFoundException extends Exception {
	/**
	 * serial Version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * When the NameNotFound.
	 */
	public NameNotFoundException() {
		super();
	}
	/**
	 * When the NameNotFound.
	 * @param msg The message
	 */
	public NameNotFoundException(final String msg) {
		super(msg);
	}
	/**
	 * When the NameNotFound.
	 * @param msg The message
	 * @param cause Cause by
	 */
	public NameNotFoundException(final String msg, final Throwable cause) {
		super(msg, cause);
	}
}
