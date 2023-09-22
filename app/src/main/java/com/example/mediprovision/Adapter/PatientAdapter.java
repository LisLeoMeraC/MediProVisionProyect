package com.example.mediprovision.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mediprovision.Models.Patient;
import com.example.mediprovision.R;
import com.example.mediprovision.diagnosticoPatient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private List<Patient> patients;

    public PatientAdapter(List<Patient> patients) {
        this.patients = patients;
        Log.d("PatientAdapter", "Size of patients list: " + patients.size());
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("PatientAdapter", "Creating view for item type: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        Log.d("PatientAdapter", "Binding data for item at position: " + position);
        Patient patient = patients.get(position);
        Log.d("PatientAdapter", "Patient data: " + patient.getName() + ", " + patient.getLastName() + ", " + patient.getIdCard());
        holder.patientNameTextView.setText(patient.getName());
        holder.patientLastNameTextView.setText(patient.getLastName());
        holder.patientIdCardTextView.setText(patient.getIdCard());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), diagnosticoPatient.class);
                intent.putExtra("idCard", patient.getIdCard());
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return patients.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView patientNameTextView, patientLastNameTextView, patientIdCardTextView;

        public PatientViewHolder(View itemView) {
            super(itemView);
            patientNameTextView = itemView.findViewById(R.id.patientNameTextView);
            patientLastNameTextView = itemView.findViewById(R.id.patientLastNameTextView);
            patientIdCardTextView = itemView.findViewById(R.id.patientIdCardTextView);
        }
    }
}
