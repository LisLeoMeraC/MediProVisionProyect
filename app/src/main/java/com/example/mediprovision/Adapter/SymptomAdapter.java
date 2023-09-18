package com.example.mediprovision.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
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
    private final OnSymptomListener onSymptomListener;

    public interface OnSymptomListener {
        void onSymptomNameChanged(int position, String newText);
    }

    public SymptomAdapter(List<Symptom> symptoms, OnSymptomListener onSymptomListener) {
        this.symptoms = symptoms;
        this.onSymptomListener = onSymptomListener;
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_symptom, parent, false);
        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SymptomViewHolder holder, int position) {
        Symptom symptom = symptoms.get(position);
        holder.symptomNameEditText.setText(symptom.getName());

        holder.symptomNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Empty on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Empty on purpose
            }

            @Override
            public void afterTextChanged(Editable s) {
                int actualPosition = holder.getAdapterPosition();
                if (actualPosition != RecyclerView.NO_POSITION) {
                    onSymptomListener.onSymptomNameChanged(actualPosition, s.toString());
                }
            }
        });
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
