package com.example.mediprovision.Models;

public class Symptom {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "name='" + name +
                '}';
    }


}
