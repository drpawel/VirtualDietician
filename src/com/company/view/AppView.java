package com.company.view;

import com.company.controller.ViewListener;
import com.company.model.Patient;
import com.company.model.PatientTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * AppView Class
 */
public class AppView extends JFrame implements ActionListener {
    private ViewListener viewListener = null;

    private final PatientTableModel patientTableModel = new PatientTableModel();
    private final JTable table = new JTable(patientTableModel);

    private final JButton addPatientButton = new JButton("Add Patient");
    private final JButton deletePatientButton = new JButton("Delete Patient");
    private final JButton addMeasurementButton = new JButton("Add Measurement");
    private final JButton showStatsButton = new JButton("Show stats");
    private final JButton exitButton = new JButton("Exit");

    /**
     * AppView constructor
     */
    public AppView() {
        this.getContentPane().add(prepareMainPanel());
        setFrame();
    }

    /**
     * creating MainPanel function
     * @return MainPanel
     */
    private JPanel prepareMainPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(600,450));

        mainPanel.add(BorderLayout.WEST, prepareButtonPanel());
        mainPanel.add(BorderLayout.CENTER,prepareDataPanel());

        mainPanel.setBorder((BorderFactory.createTitledBorder("Virtual dietician application ")));

        return mainPanel;
    }

    /**
     * creating ButtonPanel function
     * @return ButtonPanel
     */
    private JPanel prepareButtonPanel(){
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(5,1,11, 25));

        addPatientButton.addActionListener(this);
        buttonPanel.add(addPatientButton);
        deletePatientButton.addActionListener(this);
        buttonPanel.add(deletePatientButton);
        addMeasurementButton.addActionListener(this);
        buttonPanel.add(addMeasurementButton);
        showStatsButton.addActionListener(this);
        buttonPanel.add(showStatsButton);
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    /**
     * creating DataPanel function
     * @return DataPanel
     */
    private JPanel prepareDataPanel(){
        JPanel tmp = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        tmp.add(scrollPane);
        return tmp;
    }

    /**
     *Setting frame
     */
    private void setFrame(){
        setTitle("Virtual Dietician");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Setting PatientList
     * @param patients ArrayList of Patients
     */
    public void setPatientList(ArrayList<Patient> patients) {
        patientTableModel.setPatients(patients);
        patientTableModel.fireTableDataChanged();
    }

    /**
     *  Getter of current Patient's pesel
     * @return pesel
     */
    public String getCurrentPatientPesel(){
        int index = table.getSelectedRow();
        if(index == -1){
            return "none";
        }else{
            return (String) table.getValueAt(index,2);
        }
    }

    /**
     *  Add listener function
     * @param viewListener ViewListener interface
     */
    public void addListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }

    /**
     *  actionPerformed function
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.viewListener.viewChanged(this,e.getActionCommand());
    }
}
