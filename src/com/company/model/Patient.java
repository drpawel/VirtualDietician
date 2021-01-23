package com.company.model;

import java.util.ArrayList;

/**
 * Class representing patient
 */
public class Patient {
    private String name,pesel;
    private float height;

    /**
     * Patient constructor
     * @param name Patient's name
     * @param pesel Patient's pesel
     * @param height Patient's height
     */
    public Patient(String name, String pesel, float height) {
        this.name = name;
        this.pesel = pesel;
        this.height = height;
    }

    /**
     * Name getter
     * @return pesel Patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Pesel getter
     * @return pesel Patient's pesel
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * Height getter
     * @return pesel Patient's height
     */
    public float getHeight() {
        return height;
    }
}
