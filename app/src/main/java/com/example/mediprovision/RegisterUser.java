package com.example.mediprovision;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RegisterUser extends AppCompatActivity {

    private EditText txtEspecialidad;
    private RadioGroup rdnRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        txtEspecialidad=findViewById(R.id.specialtyEditText);
        rdnRol=findViewById(R.id.roleSelector);

        rdnRol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.doctorRadioButton){
                    txtEspecialidad.setVisibility(View.VISIBLE);
                }
                else{
                    txtEspecialidad.setVisibility(View.GONE);
                }
            }
        });


    }
}