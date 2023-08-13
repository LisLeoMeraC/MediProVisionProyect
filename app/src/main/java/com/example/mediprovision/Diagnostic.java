package com.example.mediprovision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Diagnostic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        Button btnRegisterSymptom= findViewById(R.id.addNewDiagnosisButton);
        btnRegisterSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Diagnostic.this,SymptomsRegister.class);
                startActivity(intent);
            }
        });
    }
}