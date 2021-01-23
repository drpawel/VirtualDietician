package com.company.model;

import java.util.ArrayList;

public class Patient {
    private String name,pesel;
    private float height;

    public Patient(String name, String pesel, float height) {
        this.name = name;
        this.pesel = pesel;
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
