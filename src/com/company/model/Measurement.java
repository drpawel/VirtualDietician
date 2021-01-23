package com.company.model;

/**
 * Class representing measurement
 */
public class Measurement {
    private float weight;
    private float BMI;
    private String date;

    /**
     * Measurement constructor
     * @param weight Measured weight
     * @param BMI Calculated BMI
     * @param date date of measurement
     */
    public Measurement(float weight, float BMI, String date) {
        this.weight = weight;
        this.BMI = BMI;
        this.date = date.split(" ")[0];
    }

    /**
     * Weight getter
     * @return weight Measured weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * BMI getter
     * @return BMI Calculated BMI
     */
    public float getBMI() {
        return BMI;
    }

    /**
     * date getter
     * @return date date of measurement
     */
    public String getDate() {
        return date;
    }
}
