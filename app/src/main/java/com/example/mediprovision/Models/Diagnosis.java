package com.example.mediprovision.Models;

import java.util.List;

public class Diagnosis {
    private int id;
    private int idRecord;
    private int idDoctor;
    private String dateDiagnosis;
    private String diagnosis;
    private String weight;
    private String height;
    private List<Symptom> sintomas;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getDateDiagnosis() {
        return dateDiagnosis;
    }

    public void setDateDiagnosis(String dateDiagnosis) {
        this.dateDiagnosis = dateDiagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public List<Symptom> getSintomas() {
        return sintomas;
    }

    public void setSintomas(List<Symptom> sintomas) {
        this.sintomas = sintomas;
    }
}
