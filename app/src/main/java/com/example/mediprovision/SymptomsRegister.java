package com.example.mediprovision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.mediprovision.Adapter.SymptomAdapter;
import com.example.mediprovision.Models.Symptom;

import java.util.ArrayList;
import java.util.List;

public class SymptomsRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_register);

        RecyclerView symptomsRecyclerView = findViewById(R.id.symptomsRecyclerView);
        List<Symptom> symptomsList = new ArrayList<>();
        SymptomAdapter adapter = new SymptomAdapter(symptomsList);
        symptomsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        symptomsRecyclerView.setAdapter(adapter);

        Button addSymptomButton = findViewById(R.id.addSymptomButton);
        addSymptomButton.setOnClickListener(v -> {
            symptomsList.add(new Symptom("", "", ""));
            adapter.notifyDataSetChanged();
        });

        Button submitReportButton = findViewById(R.id.submitReportButton);
        submitReportButton.setOnClickListener(v -> {
            // Lógica para guardar el informe y los síntomas en la base de datos
        });
    }
}