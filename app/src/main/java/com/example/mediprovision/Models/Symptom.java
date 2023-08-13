package com.example.mediprovision.Models;

public class Symptom {
    private String name;           // Nombre del síntoma
    private String description;    // Descripción del síntoma
    private String date;           // Fecha desde que se presentó el síntoma

    // Constructor
    public Symptom(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
