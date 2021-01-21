package com.company.model;

public class Measurement {
    private float weight;
    private float BMI;
    private String Date;

    public Measurement(float weight, float BMI, String date) {
        this.weight = weight;
        this.BMI = BMI;
        Date = date;
    }

    public float getWeight() {
        return weight;
    }

    public float getBMI() {
        return BMI;
    }

    public String getDate() {
        return Date;
    }
}
