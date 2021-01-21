package com.company.controller;

import com.company.view.AppView;

public interface ViewListener {
    public void viewChanged(AppView appView, String command);
}
