package com.company.model;

import java.util.ArrayList;

public class Patient {
    private String pesel,name;
    private float height;
    private ArrayList<Measurement> measurements = new ArrayList<>();

    public Patient(String pesel, String name, float height) {
        this.pesel = pesel;
        this.name = name;
        this.height = height;
    }

    public String getPesel() {
        return pesel;
    }

    public String getName() {
        return name;
    }

    public float getHeight() {
        return height;
    }
}
