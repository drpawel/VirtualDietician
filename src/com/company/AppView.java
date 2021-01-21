package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppView extends JFrame implements ActionListener {
    private ViewListener viewListener;

    public AppView() {
    }

    public void addListener(ViewListener viewListener){
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.viewListener.viewChanged(this,e.getActionCommand());
    }
}
