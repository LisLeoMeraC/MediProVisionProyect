package com.example.mediprovision.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediprovision.Models.Symptom;
import com.example.mediprovision.R;

import java.util.List;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder> {
    private final List<Symptom> symptoms;

    public SymptomAdapter(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_symptom, parent, false);
        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        Symptom symptom = symptoms.get(position);
        holder.symptomNameEditText.setText(symptom.getName());
    }

    @Override
    public int getItemCount() {
        return symptoms.size();
    }

    public static class SymptomViewHolder extends RecyclerView.ViewHolder {
        final EditText symptomNameEditText;
        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            symptomNameEditText = itemView.findViewById(R.id.symptomNameEditText);
        }
    }
}

