package com.company.model;

import java.util.ArrayList;

public class Patient {
    private String name;
    private float height;
    private ArrayList<Measurement> measurements = new ArrayList<>();

    public Patient(String name, float height) {
        this.name = name;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public float getHeight() {
        return height;
    }
}
