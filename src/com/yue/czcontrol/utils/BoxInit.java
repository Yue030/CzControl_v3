package com.yue.czcontrol.utils;

import com.jfoenix.controls.JFXComboBox;
import com.yue.czcontrol.exception.NameNotFoundException;

public interface BoxInit {
    /**
     * Set the ComboBox's item.
     * @param box JComboBox
     */
    void initBox(JFXComboBox<String> box);

    /**
     * Use member name to get ID.
     * @param name member's name
     * @return String name
     * @throws NameNotFoundException When the NameNotFound
     */
    String getID(String name) throws NameNotFoundException;
}
