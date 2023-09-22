package com.example.mediprovision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mediprovision.Adapter.PatientAdapter;
import com.example.mediprovision.Models.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Diagnostic extends AppCompatActivity {
    private RecyclerView patientsRecyclerView;
    private PatientAdapter patientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        Button btnRegisterSymptom = findViewById(R.id.addNewDiagnosisButton);
        btnRegisterSymptom.setOnClickListener(v -> {
            Intent intent = new Intent(Diagnostic.this, SymptomsRegister.class);
            startActivity(intent);
        });

        patientsRecyclerView = findViewById(R.id.diagnosisHistoryRecyclerView);
        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        int doctorId = sharedPreferences.getInt("idDoctor", 0);
        fetchPatients(doctorId);
    }

    private void fetchPatients(int doctorId) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://predictiondisease-f9c214677cde.herokuapp.com/api/findListPatient?idDoctor=" + doctorId;

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("API_RESPONSE", responseData);

                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        JSONArray jsonArray = jsonObject.getJSONArray("pacientes");
                        List<Patient> patients = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject patientObject = jsonArray.getJSONObject(i);
                            Patient patient = new Patient(
                                    patientObject.getInt("idPatient"),
                                    patientObject.getString("name"),
                                    patientObject.getString("lastName"),
                                    patientObject.getString("idCard")
                            );
                            patients.add(patient);
                        }

                        runOnUiThread(() -> {
                            patientAdapter = new PatientAdapter(patients);
                            patientsRecyclerView.setAdapter(patientAdapter);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
