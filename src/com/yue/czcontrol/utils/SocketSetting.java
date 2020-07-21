package com.yue.czcontrol.utils;

import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.exception.UploadFailedException;

public interface SocketSetting {
    /**
	 * add Data to DataBase.
	 * @param msg msg
	 * @throws UploadFailedException upload failed
	 * @throws DBCloseFailedError DataBase Object Close Failed
	 */
	void addData(final String msg) throws UploadFailedException, DBCloseFailedError;
	/**
	 * get data from database.
	 * @throws DBCloseFailedError DataBase Object Close Failed
	 */
	void initData() throws DBCloseFailedError;
	/**
	 * send Message to server.
	 * @param msg msg
	 */
	void message(final String msg);
}
