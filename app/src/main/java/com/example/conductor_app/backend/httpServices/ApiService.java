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

    @POST("comunicador/registrar/conductor")
    Call<DataPackage<Object>> registrarDatos(@Body Object o);

    @GET("comunicador/actualizar/patrones")
    Call<DataPackage<List<PatronPatente>>> obtenerPatrones();

    @GET("comunicador/actualizar/poligonos")
    Call<DataPackage<List<PoligonoDTO>>> obtenerPoligonos();

    @GET("comunicador/actualizar/horarios")
    Call<DataPackage<List<HorarioEstacionamientoDTO>>> obtenerHorarios();
}
