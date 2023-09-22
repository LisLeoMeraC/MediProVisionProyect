package com.example.mediprovision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mediprovision.Adapter.SymptomAdapter;
import com.example.mediprovision.Models.PostRequestCallback;
import com.example.mediprovision.Models.Symptom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SymptomsRegister extends AppCompatActivity implements SymptomAdapter.OnSymptomListener {

    private EditText cedulaEditText;
    private EditText patientNameEditText;
    private Button searchButton;
    private List<Symptom> symptomsList;
    private SymptomAdapter adapter;


    private EditText titleEditText, dateEditText, weightEditText, heightEditText;
    private Button  addSymptomButton, submitReportButton;
    private int pacienteId;
    private int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_register);


        RecyclerView symptomsRecyclerView = findViewById(R.id.symptomsRecyclerView);
        symptomsList = new ArrayList<>();
        adapter = new SymptomAdapter(symptomsList, this);
        symptomsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        symptomsRecyclerView.setAdapter(adapter);


        titleEditText = findViewById(R.id.titleEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);


        Button addSymptomButton = findViewById(R.id.addSymptomButton);
        addSymptomButton.setOnClickListener(v -> {
            symptomsList.add(new Symptom(""));
            adapter.notifyDataSetChanged();
        });

        // Botón para enviar el informe
        Button submitReportButton = findViewById(R.id.submitReportButton);
        submitReportButton.setOnClickListener(v -> {
            String titulo = titleEditText.getText().toString();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String fecha = dateFormat.format(calendar.getTime());
            String peso = weightEditText.getText().toString();
            String altura = heightEditText.getText().toString();

            // Crear una lista de HashMaps para los síntomas
            List<Map<String, String>> sintomas_diagnostico = new ArrayList<>();
            for (Symptom symptom : symptomsList) {
                Map<String, String> item = new HashMap<>();
                item.put("symptom_nombre", symptom.getName());
                sintomas_diagnostico.add(item);
            }

            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            int doctorId = sharedPreferences.getInt("idDoctor", 0);
            int pacienteId = sharedPreferences.getInt("id", 0);

            // Crear el objeto JSON para enviar
            JSONObject payload = new JSONObject();
            try {
                payload.put("paciente_id", pacienteId);
                payload.put("doctor_id", doctorId);
                payload.put("titulo", titulo);
                payload.put("fecha", fecha);
                payload.put("peso", peso);
                payload.put("altura", altura);
                payload.put("sintomas_diagnostico", new JSONArray(sintomas_diagnostico));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("JSON Payload", payload.toString());
            makePostRequest("https://predictiondisease-f9c214677cde.herokuapp.com/api/registerSymptom", payload, this);
        });

        // Inicializar campos y botones adicionales
        cedulaEditText = findViewById(R.id.cedulaEditText);
        patientNameEditText = findViewById(R.id.patientNameEditText);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> {
            String cedula = cedulaEditText.getText().toString();
            buscarPaciente(cedula);
        });

        cedulaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    patientNameEditText.setText("");
                }
            }
        });
    }


    private void makePostRequest(String url, JSONObject jsonPayload, Context context) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonPayload.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("OKHttp", responseData);

                    // Realizar acciones en el thread principal
                    new Handler(Looper.getMainLooper()).post(() -> {
                        // Mostrar el Toast
                        Toast.makeText(context, "Diagnóstico registrado", Toast.LENGTH_SHORT).show();

                        // Redirigir a la siguiente Activity
                        Intent intent = new Intent(context, Diagnostic.class);
                        context.startActivity(intent);
                    });
                } else {
                    Log.d("OKHttp", "Response Code: " + response.code());
                    Log.d("OKHttp", "Response Message: " + response.message());
                    if (response.body() != null) {
                        Log.d("OKHttp", "Response Body: " + response.body().string());
                    }
                }
            }
        });
    }


    // Implementar la función de la interfaz OnSymptomListener
    @Override
    public void onSymptomNameChanged(int position, String newText) {
        // Actualizar el nombre del síntoma en la lista symptomsList
        symptomsList.get(position).setName(newText);
    }

    //Metodo para buscar paciente
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
                runOnUiThread(() -> Toast.makeText(SymptomsRegister.this, "Error en la búsqueda", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        JSONObject patientWrapper = jsonObject.optJSONObject("paciente");
                        if (patientWrapper != null) {
                            JSONObject detalle = patientWrapper.optJSONObject("detalle");
                            if (detalle != null) {
                                int idPatient = detalle.optInt("id", -1);


                                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id", idPatient);
                                editor.apply();

                                String name = patientWrapper.optJSONObject("paciente").optString("name", "");
                                String lastName = patientWrapper.optJSONObject("paciente").optString("lastName", "");
                                final String fullName = name + " " + lastName;

                                runOnUiThread(() -> patientNameEditText.setText(fullName));
                            } else {
                                runOnUiThread(() -> Toast.makeText(SymptomsRegister.this, "Detalle no encontrado", Toast.LENGTH_SHORT).show());
                            }
                        } else {
                            runOnUiThread(() -> Toast.makeText(SymptomsRegister.this, "Paciente no encontrado", Toast.LENGTH_SHORT).show());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(SymptomsRegister.this, "Error en el formato del JSON", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(SymptomsRegister.this, "Paciente no encontrado", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}