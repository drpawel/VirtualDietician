package com.company.model;

public class Patient {
    private String name,surname;
    private int height;

    public Patient(String name, String surname, int height) {
        this.name = name;
        this.surname = surname;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getHeight() {
        return height;
    }
}
