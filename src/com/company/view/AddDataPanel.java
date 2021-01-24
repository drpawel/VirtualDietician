package com.company.view;

import javax.swing.*;
import java.awt.*;

/**
 * AddDataPanel Class
 */
public class AddDataPanel extends JFrame {
    private JButton submitButton = new JButton("Submit");
    private JTextField weightTextField = new JTextField();

    public AddDataPanel(){
        this.getContentPane().add(prepareMainPanel());
        setFrame();
    }

    /**
     * creating MainPanel function
     * @return MainPanel
     */
    private JPanel prepareMainPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(250,100));

        mainPanel.add(prepareOptionsPanel(),BorderLayout.CENTER);
        mainPanel.setBorder((BorderFactory.createTitledBorder("Add patients body weight: ")));
        mainPanel.add(submitButton,BorderLayout.PAGE_END);

        return mainPanel;
    }

    /**
     * creating OptionPanel function
     * @return OptionsPanel
     */
    private JPanel prepareOptionsPanel(){
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel ("Weight: "));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));

        weightTextField.setPreferredSize(new Dimension(150,20));
        weightTextField.setMaximumSize((new Dimension(150,20)));
        weightTextField.setMinimumSize((new Dimension(150,20)));
        optionsPanel.add(weightTextField);

        return optionsPanel;
    }

    /**
     * Returning patients weight function
     * @return weight
     */
    public float getWeight(){
        return Float.valueOf(weightTextField.getText());
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
        setTitle("Add data");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
