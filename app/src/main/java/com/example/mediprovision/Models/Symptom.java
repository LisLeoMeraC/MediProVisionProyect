package com.example.mediprovision.Models;

public class Symptom {
    private String name;

    // Constructor
    public Symptom(String name) {
        this.name = name;

    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Symptom{" +
                "name='" + name +
                '}';
    }
}
