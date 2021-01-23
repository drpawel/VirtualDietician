package com.company.controller;

import com.company.model.AppModel;
import com.company.model.Patient;
import com.company.view.AddDataPanel;
import com.company.view.AddPatientPanel;
import com.company.view.AppView;
import com.company.view.ChartPanel;

public class AppController implements ViewListener, ModelListener {
    private final AppView appView;
    private final AppModel appModel;

    public AppController(AppView appView, AppModel appModel) {
        this.appView = appView;
        this.appModel = appModel;
    }

    @Override
    public void modelChanged(AppModel appModel) {
        //appView.setPatientList(appModel.getPatientsList());
    }

    @Override
    public void viewChanged(AppView appView, String command) {
        switch (command) {
            case "Add Patient":
                System.out.println("ADD");
                AddPatientPanel addPatientPanel = new AddPatientPanel();
                addPatientPanel.getSubmitButton().addActionListener(e -> {
                    System.out.println("ADDED Patient");
                    //appModel.insertPatientToDataBase(addPatientPanel.getPatientName(),addPatientPanel.getPatientPesel(),addPatientPanel.getPatientHeight());
                });
                break;
            case "Delete Patient":
                System.out.println("DELETE");
                //appModel.deletePatientFromDataBase(appView.getCurrentPatientPesel());
                break;
            case "Add Measurement":
                System.out.println("MEASUREMENT");
                AddDataPanel addDataPanel = new AddDataPanel();
                addDataPanel.getSubmitButton().addActionListener(e -> {
                    System.out.println("ADDED MEASUREMENT");
                    //appModel.insertMeasurementToDataBase(addDataPanel.getWeight(),addDataPanel.getBMI(),appView.getCurrentPatientPesel());
                });
                break;
            case "Show stats":
                System.out.println("SHOW");
                //new ChartPanel(appModel.getMeasurementsList(appView.getCurrentPatientPesel()));
                break;
            case "Exit":
                System.out.println("EXIT");
                appModel.shutdownDataBase();
                break;
        }
    }
}
