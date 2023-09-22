package com.example.mediprovision.Models;

import com.example.mediprovision.Models.SymptomRequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/registerSymptom")
    Call<Void> registerSymptoms(@Body SymptomRequestBody requestBody);
}
