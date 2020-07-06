package com.yue.czcontrol.exception;

public class UploadFailedException extends Exception {
	/**
	 * serial Version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Upload data failed.
	 */
	public UploadFailedException() {
		super();
	}
	/**
	 * Upload data failed.
	 * @param msg The message
	 */
	public UploadFailedException(final String msg) {
		super(msg);
	}
	/**
	 * Upload data failed.
	 * @param msg The message
	 * @param cause Cause by
	 */
	public UploadFailedException(final String msg, final Throwable cause) {
		super(msg, cause);
	}
}
