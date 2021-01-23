package com.company;

import com.company.controller.AppController;
import com.company.model.AppModel;
import com.company.view.AppView;

import javax.swing.*;

/**
 * Main function
 */
public class VirtualDietician {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppView appView = new AppView();
            AppModel appModel = new AppModel();
            AppController appController = new AppController(appView,appModel);
            appView.addListener(appController);
            appModel.addListener(appController);
        });
    }
}
