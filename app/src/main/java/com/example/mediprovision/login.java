package com.example.mediprovision;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mediprovision.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.submitLoginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://predictiondisease-f9c214677cde.herokuapp.com/api/finduser?";

        RequestBody formBody = new FormBody.Builder()
                .add("user", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        boolean userValid = jsonObject.optBoolean("Usuario", false);
                        boolean passwordValid = jsonObject.optBoolean("Password", false);

                        if (userValid && passwordValid) {
                            boolean isDoctor = jsonObject.optBoolean("doctor", false);
                            boolean isPatient = jsonObject.optBoolean("paciente", false);

                            runOnUiThread(() -> {
                                Intent intent;
                                if (isDoctor) {
                                    intent = new Intent(login.this, MainActivity.class);
                                } else if (isPatient) {
                                    intent = new Intent(login.this, RegisterUser.class);
                                } else {
                                    intent = new Intent(login.this, Home.class);
                                }
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(() -> Toast.makeText(login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    runOnUiThread(() -> Toast.makeText(login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}

