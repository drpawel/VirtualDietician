package com.company;

import javax.swing.*;

/**
 * class with MessageDialogs
 */
public class DialogLibrary {

    /**
     * MessageDialog - "There is no patient!"
     */
    public static void showNoPatientDialog() {
        JOptionPane.showMessageDialog(null,
                "There is no patient!",
                "Warning",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * MessageDialog - "There is no any measurement!"
     */
    public static void showNoMeasureDialog() {
        JOptionPane.showMessageDialog(null,
                "There is no any measurement!",
                "Warning",
                JOptionPane.ERROR_MESSAGE);
    }
}
