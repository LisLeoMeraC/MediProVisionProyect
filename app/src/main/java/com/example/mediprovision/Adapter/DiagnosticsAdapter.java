package com.example.mediprovision.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediprovision.Models.Diagnosis;
import com.example.mediprovision.Models.Symptom;
import com.example.mediprovision.R;

import java.util.List;

public class DiagnosticsAdapter extends RecyclerView.Adapter<DiagnosticsAdapter.DiagnosisViewHolder> {
    private List<Diagnosis> diagnosisList;
    private Context context;

    public DiagnosticsAdapter(List<Diagnosis> diagnosisList, Context context) {
        this.diagnosisList = diagnosisList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diagnosis, parent, false);
        return new DiagnosisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisViewHolder holder, int position) {
        Diagnosis diagnosis = diagnosisList.get(position);
        holder.dateDiagnosis.setText("Date of Diagnosis: " + diagnosis.getDateDiagnosis());
        holder.diagnosis.setText("Diagnosis: " + diagnosis.getDiagnosis());
        holder.weightHeight.setText("Weight: " + diagnosis.getWeight() + "kg, Height: " + diagnosis.getHeight() + "cm");

        // Limpiar cualquier vista previa en el contenedor de síntomas
        holder.symptomsListContainer.removeAllViews();


        for (Symptom symptom : diagnosis.getSintomas()) {
            TextView symptomTextView = new TextView(context);
            symptomTextView.setText(symptom.getName());
            symptomTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 4, 0, 4);  // Ajusta los márgenes si es necesario
            symptomTextView.setLayoutParams(layoutParams);
            holder.symptomsListContainer.addView(symptomTextView);
        }
    }

    @Override
    public int getItemCount() {
        return diagnosisList.size();
    }

    class DiagnosisViewHolder extends RecyclerView.ViewHolder {
        TextView dateDiagnosis, diagnosis, weightHeight;
        LinearLayout symptomsListContainer;

        DiagnosisViewHolder(@NonNull View itemView) {
            super(itemView);
            dateDiagnosis = itemView.findViewById(R.id.dateDiagnosisTextView);
            diagnosis = itemView.findViewById(R.id.diagnosisTextView);
            weightHeight = itemView.findViewById(R.id.weightHeightTextView);
            symptomsListContainer = itemView.findViewById(R.id.symptomsListContainer);
        }
    }
}

