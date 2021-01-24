package com.company.view;

import com.company.controller.ViewListener;
import com.company.model.Patient;

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
    private ArrayList<Patient> patients = new ArrayList<>();
    private JTable jt = new JTable();
    private JPanel dataPanel = new JPanel();
    private JButton addPatientButton = new JButton("Add Patient");
    private JButton deletePatientButton = new JButton("Delete Patient");
    private JButton addMeasurementtButton = new JButton("Add Measurement");
    private JButton showStatsButton = new JButton("Show stats");
    private JButton exitButton = new JButton("Exit");

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
        mainPanel.add(BorderLayout.CENTER,prepareDataPanel(patients));

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
        addMeasurementtButton.addActionListener(this);
        buttonPanel.add(addMeasurementtButton);
        showStatsButton.addActionListener(this);
        buttonPanel.add(showStatsButton);
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    /**
     * creating DataPanel function
     * @param patients
     * @return DataPanel
     */
    private JPanel prepareDataPanel(ArrayList<Patient> patients){

        Patient test = new Patient("990909", "Maria", 166);
        patients.add(test);

        dataPanel = new JPanel();
        int size = patients.size();
        String[][] data = new String [size][3];
        for(int i=0; i<patients.size(); i++ ) {

            String height = Float.toString(patients.get(i).getHeight());
            data[i][0] = patients.get(i).getName();
            data[i][1] = height;
            data[i][2] = patients.get(i).getPesel();

        }
        String column[]={"NAME","HEIGHT","PESEL"};
        jt=new JTable(data,column);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jt);

        dataPanel.add(scrollPane);

        return dataPanel;
    }

    /**
     *Setting frame
     */
    private void setFrame(){
        setTitle("Add data");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     *
     * @param patients
     */
    public void addPatientList(ArrayList<Patient> patients){
        this.patients = patients;
        this.dataPanel = prepareDataPanel(patients);
        this.dataPanel.repaint();
    }

    /**
     *
     * @return pesel
     */
    public String getCurrentPatientPesel(){
        return (String) jt.getValueAt(jt.getSelectedRow(),2);
    }

    /**
     *
     * @param viewListener
     */
    public void addListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.viewListener.viewChanged(this,e.getActionCommand());
    }
}
