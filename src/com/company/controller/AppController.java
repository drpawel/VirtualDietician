package com.company.controller;

import com.company.model.AppModel;
import com.company.view.AddDataPanel;
import com.company.view.AddPatientPanel;
import com.company.view.AppView;
import com.company.view.ChartWindow;

import java.util.Objects;

/**
 * AppController class
 */
public class AppController implements ViewListener, ModelListener {
    private final AppView appView;
    private final AppModel appModel;

    /**
     * AppController constructor
     * @param appView appView of the app
     * @param appModel  appModel of the app
     */
    public AppController(AppView appView, AppModel appModel) {
        this.appView = appView;
        this.appModel = appModel;
    }

    /**
     * Overridden ModelListener function
     * @param appModel Corresponding appModel
     */
    @Override
    public void modelChanged(AppModel appModel) {
        appView.setPatientList(appModel.getPatientsList());
    }

    /**
     * Overridden ViewListener function
     * @param appView Corresponding appModel
     * @param command Corresponding command from ActionListener
     */
    @Override
    public void viewChanged(AppView appView, String command) {
        switch (command) {
            /*
             * Adding Patient to database
             */
            case "Add Patient":
                AddPatientPanel addPatientPanel = new AddPatientPanel();
                addPatientPanel.getSubmitButton().addActionListener(e -> {
                    appModel.insertPatientToDataBase(addPatientPanel.getPatientName(),
                            addPatientPanel.getPatientPesel(),addPatientPanel.getPatientHeight());
                    addPatientPanel.dispose();
                });
                break;

            /*
             * Deleting Patient from database
             */
            case "Delete Patient":
                if(Objects.equals(appView.getCurrentPatientPesel(), "none")){
                    com.company.DialogLibrary.showNoPatientDialog();
                    break;
                }
                appModel.deletePatientFromDataBase(appView.getCurrentPatientPesel());
                com.company.DialogLibrary.showPatientDeletedDialog();
                break;

            /*
             * Adding Measurement to database
             */
            case "Add Measurement":
                if(Objects.equals(appView.getCurrentPatientPesel(), "none")){
                    com.company.DialogLibrary.showNoPatientDialog();
                    break;
                }
                AddDataPanel addDataPanel = new AddDataPanel();
                addDataPanel.getSubmitButton().addActionListener(e -> {
                    float weight = addDataPanel.getWeight();
                    float height = appModel.getPatientHeight(appView.getCurrentPatientPesel())/100;
                    float BMI = (weight/(height*height));
                    appModel.insertMeasurementToDataBase(weight,BMI,appView.getCurrentPatientPesel());
                    addDataPanel.dispose();
                });
                break;

            /*
             * Showing statistics
             */
            case "Show stats":
                if(Objects.equals(appView.getCurrentPatientPesel(), "none")){
                    com.company.DialogLibrary.showNoPatientDialog();
                    break;
                }
                new ChartWindow(appModel.getMeasurementsList(appView.getCurrentPatientPesel()));
                break;

            /*
             * Closing app
             */
            case "Exit":
                appModel.shutdownDataBase();
                System.exit(0);
                break;
        }
    }
}
