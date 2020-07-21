package com.yue.czcontrol.utils;

import com.jfoenix.controls.JFXComboBox;
import com.yue.czcontrol.error.DBCloseFailedError;
import com.yue.czcontrol.exception.NameNotFoundException;

public interface BoxInit {
    /**
     * Set the ComboBox's item.
     * @param box JComboBox
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    void initBox(JFXComboBox<String> box) throws DBCloseFailedError;

    /**
     * Use member name to get ID.
     * @param name member's name
     * @return String name
     * @throws NameNotFoundException When the NameNotFound
     * @throws DBCloseFailedError DataBase Object Close Failed
     */
    String getID(String name) throws NameNotFoundException, DBCloseFailedError;
}
