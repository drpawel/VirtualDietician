package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController implements ViewListener,ModelListener {
    private final AppView appView;
    private final AppModel appModel;

    public AppController(AppView appView, AppModel appModel) {
        this.appView = appView;
        this.appModel = appModel;
    }

    @Override
    public void modelChanged(AppModel appModel) {
        //TODO change AppView
    }

    @Override
    public void viewChanged(AppView appView, String command) {
        switch (command) {
            case "Add Patient":
                System.out.println("ADD");
                break;
            case "Delete Patient":
                System.out.println("DELETE");
                break;
            case "Add Measurement":
                System.out.println("MEASURE");
                break;
            case "Show stats":
                System.out.println("SHOW");
                break;
        }
    }
}
