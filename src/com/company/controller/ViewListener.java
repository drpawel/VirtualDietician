package com.company.controller;

import com.company.view.AppView;

/**
 * View Listener class
 */
public interface ViewListener {
    void viewChanged(AppView appView, String command);
}
