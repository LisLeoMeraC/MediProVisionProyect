package com.example.mediprovision.Models;

public class Patient {
    private int idPatient;
    private String name;
    private String lastName;
    private String idCard;

    public Patient(int idPatient, String name, String lastName, String idCard) {
        this.idPatient = idPatient;
        this.name = name;
        this.lastName = lastName;
        this.idCard = idCard;
    }


    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


}
