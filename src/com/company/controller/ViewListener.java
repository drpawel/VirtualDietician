package com.company.controller;

import com.company.view.AppView;

/**
 * View Listener class
 */
public interface ViewListener {
    public void viewChanged(AppView appView, String command);
}
