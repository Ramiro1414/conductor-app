package com.example.conductor_app.backend.httpServices;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PruebaObtenerDatos {
    private static final String BASE_URL = "http://if012estm.fi.mdn.unp.edu.ar:28003/";
    private ApiService apiService;

    public PruebaObtenerDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void obtenerDatosDelServidor(DataCallback callback) {
        Call<ApiResponse> call = apiService.obtenerDatos();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    callback.onDataReceived(apiResponse.getData()); // Llama al método de la interfaz
                } else {
                    callback.onError("Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("ERROR", "Error en la petición: " + t.getMessage());
                callback.onError("Error en la petición: " + t.getMessage());
            }
        });
    }
}


