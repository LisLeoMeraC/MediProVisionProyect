package com.example.mediprovision.Models;

import com.example.mediprovision.Models.Symptom;

import java.util.List;

public class SymptomRequestBody {
    int paciente_id;
    int doctor_id;
    String titulo;
    String fecha;
    String peso;
    String altura;
    List<Symptom> sintomas_diagnostico;

    public int getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(int paciente_id) {
        this.paciente_id = paciente_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public List<Symptom> getSintomas_diagnostico() {
        return sintomas_diagnostico;
    }

    public void setSintomas_diagnostico(List<Symptom> sintomas_diagnostico) {
        this.sintomas_diagnostico = sintomas_diagnostico;
    }
}
