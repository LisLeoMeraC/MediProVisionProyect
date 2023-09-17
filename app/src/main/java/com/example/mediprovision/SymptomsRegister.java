package com.example.mediprovision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mediprovision.Adapter.SymptomAdapter;
import com.example.mediprovision.Models.Symptom;

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

public class SymptomsRegister extends AppCompatActivity {

    private EditText cedulaEditText;
    private EditText patientNameEditText;
    private Button searchButton;


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
        });

        cedulaEditText = findViewById(R.id.cedulaEditText);
        patientNameEditText = findViewById(R.id.patientNameEditText);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cedula = cedulaEditText.getText().toString();
                buscarPaciente(cedula);
            }
        });

        cedulaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    patientNameEditText.setText("");
                }
            }
        });

    }
    private void buscarPaciente(String cedula) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://predictiondisease-f9c214677cde.herokuapp.com/api/findPatient?cedula=" + cedula;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SymptomsRegister.this, "Error en la búsqueda", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        if(jsonObject.has("name") && jsonObject.has("lastName")) {
                            final String name = jsonObject.getString("name");
                            final String lastName = jsonObject.getString("lastName");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    patientNameEditText.setText(name + " " + lastName);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SymptomsRegister.this, "Paciente no encontrado", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SymptomsRegister.this, "Paciente no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SymptomsRegister.this, "Paciente no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
