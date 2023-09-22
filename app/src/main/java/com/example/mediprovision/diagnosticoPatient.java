package com.example.mediprovision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mediprovision.Adapter.DiagnosticsAdapter;
import com.example.mediprovision.Models.Diagnosis;
import com.example.mediprovision.Models.PatientData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class diagnosticoPatient extends AppCompatActivity {
    RecyclerView recyclerView;

    String identidad = getIntent().getStringExtra("idCard");

    DiagnosticsAdapter adapter;
    List<Diagnosis> diagnosisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_patient);

        recyclerView = findViewById(R.id.diagnosticsRecyclerView);
        adapter = new DiagnosticsAdapter(diagnosisList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String idCard = identidad;
        fetchPatientData(idCard);
        Log.e("Id:",idCard);
    }

    private void fetchPatientData(String idCard) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://predictiondisease-f9c214677cde.herokuapp.com/api/findRecordPatient?idCard=" + idCard;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {

                } else {
                    String jsonData = response.body().string();


                    Gson gson = new Gson();
                    PatientData patientData = gson.fromJson(jsonData, PatientData.class);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            diagnosisList.clear();
                            diagnosisList.addAll(patientData.getDiagnosticos());
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
