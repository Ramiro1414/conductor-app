package com.example.conductor_app.backend.httpServices;

import com.example.conductor_app.backend.modelo.PatronPatente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServicePrueba {
    @GET("datos/actualizar")
    Call<DataPackage<Object>> obtenerDatos();  // Este método puede quedarse igual si no necesitas un tipo específico

    @POST("datos/registrar/conductor")
    Call<DataPackage<Object>> registrarDatos(@Body Object o);  // Cambiado para usar DataPackage<Object>

    @GET("datos/patrones")
    Call<DataPackage<List<PatronPatente>>> obtenerPatrones();  // Cambiado para usar DataPackage<List<PatronPatente>>
}
