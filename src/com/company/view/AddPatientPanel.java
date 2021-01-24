package com.company.view;

import javax.swing.*;
import java.awt.*;

/**
 * AddPatientPanel Class
 */
public class AddPatientPanel extends JFrame {

    private final JButton submitButton = new JButton("Submit");
    private final JTextField nameTextField = new JTextField();
    private final JTextField peselTextField = new JTextField();
    private final JTextField heightTextField = new JTextField();

    /**
     * Add Patient Panel constructor
     */
    public AddPatientPanel(){
        this.getContentPane().add(prepareMainPanel());
        setFrame();
    }

    /**
     * creating mainPanel function
     * @return mainPanel
     */
    private JPanel prepareMainPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(200,120));
        mainPanel.setBorder((BorderFactory.createTitledBorder("Add patients data: ")));
        mainPanel.add(prepareOptionsPanel(),BorderLayout.CENTER);
        mainPanel.add(submitButton,BorderLayout.PAGE_END);

        return mainPanel;
    }

    /**
     * creating optionsPanel function
     * @return optionsPanel
     */
    private JPanel prepareOptionsPanel(){
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3,1));

        optionsPanel.add(new JLabel ("Name: "));
        optionsPanel.add(nameTextField);

        optionsPanel.add(new JLabel ("Pesel: "));
        optionsPanel.add(peselTextField);

        optionsPanel.add(new JLabel ("Height: "));
        optionsPanel.add(heightTextField);

        return optionsPanel;
    }

    /**
     *
     * @return name
     */
    public String getPatientName(){
        return nameTextField.getText();
    }

    /**
     *
     * @return pesel
     */
    public String getPatientPesel(){
        return peselTextField.getText();
    }

    /**
     *
     * @return height
     */
    public float getPatientHeight(){
        return Float.parseFloat(heightTextField.getText());
    }

    /**
     *
     * @return submitButton
     */
    public JButton getSubmitButton() {
        return submitButton;
    }

    /**
     * Setting frame
     */
    private void setFrame(){
        setTitle("Add Patient");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
