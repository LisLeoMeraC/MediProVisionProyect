package com.example.mediprovision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterUser extends AppCompatActivity {

    private EditText txtEspecialidad;
    private EditText txtDescSpeciality;
    private RadioGroup rdnRol;

    //Campos de paciente
    private EditText nameEditText;
    private EditText lastNameEditText;
    private EditText identityEditText;
    private EditText birthDateEditText;
    private Spinner genderSpinner;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText adressEditText;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        txtEspecialidad=findViewById(R.id.specialtyEditText);
        txtDescSpeciality=findViewById(R.id.descriptionEditText);
        rdnRol=findViewById(R.id.roleSelector);
        nameEditText=findViewById(R.id.firstNameEditText);
        lastNameEditText=findViewById(R.id.lastNameEditText);
        identityEditText=findViewById(R.id.NumIdentidadEditText);
        birthDateEditText=findViewById(R.id.birthDateEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        phoneEditText=findViewById(R.id.phoneEditText);
        emailEditText=findViewById(R.id.emailEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        adressEditText=findViewById(R.id.addressEditText);
        btnRegistrar= findViewById(R.id.registerButton);


        //Spinner para seleccionar el genero
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);


        //Validar correo electronico



        //Seleccionar el rol del usuario
        rdnRol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.doctorRadioButton){
                    txtEspecialidad.setVisibility(View.VISIBLE);
                    txtDescSpeciality.setVisibility(View.VISIBLE);
                    habilitarControles();
                }
                else{
                    txtEspecialidad.setVisibility(View.GONE);
                    habilitarControles();
                }
            }
        });
        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterUser.this, dateSetListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rolSeleccionado = rdnRol.getCheckedRadioButtonId();
                if (rolSeleccionado == R.id.patientRadioButton) {

                    String email = emailEditText.getText().toString().trim();

                    if (nameEditText.getText().toString().isEmpty() ||
                            lastNameEditText.getText().toString().isEmpty() ||
                            identityEditText.getText().toString().isEmpty() ||
                            birthDateEditText.getText().toString().isEmpty() ||
                            phoneEditText.getText().toString().isEmpty() ||
                            email.isEmpty() ||
                            passwordEditText.getText().toString().isEmpty() ||
                            adressEditText.getText().toString().isEmpty()) {

                        Toast.makeText(RegisterUser.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!isValidEmail(email)) {
                        emailEditText.setError("Por favor, ingrese un correo electrónico válido.");
                        return;
                    }
                    RegistrarPaciente(
                            nameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            identityEditText.getText().toString(),
                            birthDateEditText.getText().toString(),
                            genderSpinner.getSelectedItem().toString(),
                            phoneEditText.getText().toString(),
                            email,
                            passwordEditText.getText().toString(),
                            adressEditText.getText().toString()
                    );
                }
                else if(rolSeleccionado == R.id.doctorRadioButton){
                    String email = emailEditText.getText().toString().trim();

                    if (nameEditText.getText().toString().isEmpty() ||
                            lastNameEditText.getText().toString().isEmpty() ||
                            identityEditText.getText().toString().isEmpty() ||
                            txtEspecialidad.getText().toString().isEmpty()||
                            txtDescSpeciality.getText().toString().isEmpty()||
                            birthDateEditText.getText().toString().isEmpty() ||
                            phoneEditText.getText().toString().isEmpty() ||
                            email.isEmpty() ||
                            passwordEditText.getText().toString().isEmpty() ||
                            adressEditText.getText().toString().isEmpty()) {

                        Toast.makeText(RegisterUser.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!isValidEmail(email)) {
                        emailEditText.setError("Por favor, ingrese un correo electrónico válido.");
                        return;
                    }
                    RegistrarMedico(
                            nameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            birthDateEditText.getText().toString(),
                            genderSpinner.getSelectedItem().toString(),
                            phoneEditText.getText().toString(),
                            email,
                            passwordEditText.getText().toString(),
                            adressEditText.getText().toString(),
                            identityEditText.getText().toString(),
                            txtEspecialidad.getText().toString(),
                            txtDescSpeciality.getText().toString()
                    );
                }
            }

        });
    }
    //Metodo paraa registrar un paciente
    private void RegistrarPaciente(String name, String lastName,String identity, String birthDay, String gender, String cellPhone, String email, String password, String adress){
        OkHttpClient client= new OkHttpClient();

        String[] parts = birthDay.split("-");
        String formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];

        String url = "https://predictiondisease-f9c214677cde.herokuapp.com/api/registerPatient?" +
                "name=" + name +
                "&lastName=" + lastName +
                "&idNumber=" + identity+
                "&birthday=" + formattedDate +
                "&gender=" + gender +
                "&cellPhone=" + cellPhone +
                "&email=" + email +
                "&password=" + password +
                "&address=" + adress;

        Log.d("Debug", "La URL es: " + url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterUser.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    Log.d("Debug", "La respuesta es: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterUser.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterUser.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        Intent intent = new Intent(RegisterUser.this, login.class);
        startActivity(intent);
        finish();

    }

    //Registrar un medico
    private void RegistrarMedico(String name, String lastName, String birthDay, String gender, String cellPhone, String email, String password, String adress,String identity,   String speciality, String descSpec){
        OkHttpClient client= new OkHttpClient();

        String[] partes = birthDay.split("-");
        String formattedDate = partes[2] + "-" + partes[1] + "-" + partes[0];

        String url = "https://predictiondisease-f9c214677cde.herokuapp.com/api/registerDoctor?" +
                "name=" + name +
                "&lastName=" + lastName +
                "&birthday=" + formattedDate +
                "&gender=" + gender +
                "&cellPhone=" + cellPhone +
                "&email=" + email +
                "&password=" + password +
                "&address=" + adress +
                "&idNumber=" + identity +
                "&speciality="+ speciality+
                "&description="+descSpec;

        Log.d("Debug", "La URL es: " + url);

        Request request= new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterUser.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    Log.d("Debug", "La respuesta es: " + myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterUser.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterUser.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        Intent intent = new Intent(RegisterUser.this, login.class);
        startActivity(intent);
        finish();
    }





    //Para mostrar un calendario
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            birthDateEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
        }
    };
    private void habilitarControles(){
        nameEditText.setEnabled(true);
        lastNameEditText.setEnabled(true);
        identityEditText.setEnabled(true);
        birthDateEditText.setEnabled(true);
        genderSpinner.setEnabled(true);
        phoneEditText.setEnabled(true);
        emailEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        adressEditText.setEnabled(true);
        txtEspecialidad.setEnabled(true);
        txtDescSpeciality.setEnabled(true);
        btnRegistrar.setEnabled(true);
    }
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}