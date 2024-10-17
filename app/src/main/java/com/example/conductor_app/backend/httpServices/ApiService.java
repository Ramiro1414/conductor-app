package com.example.conductor_app.backend.httpServices;

import com.example.conductor_app.backend.modelo.HorarioEstacionamiento;
import com.example.conductor_app.backend.modelo.HorarioEstacionamientoDTO;
import com.example.conductor_app.backend.modelo.PatronPatente;
import com.example.conductor_app.backend.modelo.Poligono;
import com.example.conductor_app.backend.modelo.PoligonoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
//    @GET("comunicador/actualizar")
//    Call<DataPackage<Object>> obtenerDatos();  // Este método puede quedarse igual si no necesitas un tipo específico
//
    @POST("comunicador/registrar/conductor")
    Call<DataPackage<Object>> registrarDatos(@Body Object o);  // Cambiado para usar DataPackage<Object>

    @GET("comunicador/actualizar/patrones")
    Call<DataPackage<List<PatronPatente>>> obtenerPatrones();  // Cambiado para usar DataPackage<List<PatronPatente>>

    @GET("comunicador/actualizar/poligonos")
    Call<DataPackage<List<PoligonoDTO>>> obtenerPoligonos();  // Cambiado para usar DataPackage<List<PatronPatente>>

    @GET("comunicador/actualizar/horarios")
    Call<DataPackage<List<HorarioEstacionamientoDTO>>> obtenerHorarios();  // Cambiado para usar DataPackage<List<PatronPatente>>
}
