package com.example.mediprovision.Models;

import java.util.List;

public class PatientData {

    private String historial;
    private List<Diagnosis> Diagnosticos;

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public List<Diagnosis> getDiagnosticos() {
        return Diagnosticos;
    }

    public void setDiagnosticos(List<Diagnosis> diagnosticos) {
        Diagnosticos = diagnosticos;
    }


}
