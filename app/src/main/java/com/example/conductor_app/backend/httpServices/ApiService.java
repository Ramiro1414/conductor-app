package com.example.conductor_app.backend.httpServices;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("enviar/datos")
    Call<ApiResponse> obtenerDatos();
}
