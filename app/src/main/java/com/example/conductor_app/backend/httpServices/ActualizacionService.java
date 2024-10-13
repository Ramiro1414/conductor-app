package com.example.conductor_app.backend.httpServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.conductor_app.backend.Service.HorarioEstacionamientoService;
import com.example.conductor_app.backend.Service.LineaPoligonoService;
import com.example.conductor_app.backend.Service.PatronesPatentesService;
import com.example.conductor_app.backend.Service.PoligonoService;
import com.example.conductor_app.backend.Service.PuntoService;
import com.example.conductor_app.backend.modelo.HorarioEstacionamiento;
import com.example.conductor_app.backend.modelo.LineaPoligono;
import com.example.conductor_app.backend.modelo.LineaPoligonoDTO;
import com.example.conductor_app.backend.modelo.PatronPatente;
import com.example.conductor_app.backend.modelo.Poligono;
import com.example.conductor_app.backend.modelo.PoligonoDTO;
import com.example.conductor_app.backend.modelo.Punto;
import com.example.conductor_app.backend.modelo.PuntoDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActualizacionService {
    private static final String BASE_URL = "http://if012estm.fi.mdn.unp.edu.ar:28003/";
    private final ApiService apiService;
    private final Context context;

    public ActualizacionService(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void actualizarHorariosDeEstacionamiento(){
        Call<DataPackage<List<HorarioEstacionamiento>>> call = apiService.obtenerHorarios();
        call.enqueue(new Callback<DataPackage<List<HorarioEstacionamiento>>>() {
            @Override
            public void onResponse(Call<DataPackage<List<HorarioEstacionamiento>>> call, Response<DataPackage<List<HorarioEstacionamiento>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HorarioEstacionamientoService horarioEstacionamientoService = new HorarioEstacionamientoService(context);

                    List<HorarioEstacionamiento> horariosNuevos = response.body().getData();
                    horarioEstacionamientoService.deleteAll();
                    for(HorarioEstacionamiento horarioEstacionamiento : horariosNuevos) {
                        HorarioEstacionamiento nuevoHorario = new HorarioEstacionamiento(
                                horarioEstacionamiento.getFechaInicio(),
                                horarioEstacionamiento.getFechaFin(),
                                horarioEstacionamiento.getHoraInicio(),
                                horarioEstacionamiento.getHoraFin(),
                                horarioEstacionamiento.getNombre());

                        horarioEstacionamientoService.save(nuevoHorario);
                    }

                    Toast.makeText(context, "Registros recibidos exitosamente. Total horarios: " + horarioEstacionamientoService.findAll().size(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Error al recibir registro: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataPackage<List<HorarioEstacionamiento>>> call, Throwable t) {
                Log.e("DataError", "Fallo en la solicitud: " + t.getMessage());
                Toast.makeText(context, "Error al recibir registro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void actualizarPatronesDePatentes(){
        Call<DataPackage<List<PatronPatente>>> call = apiService.obtenerPatrones();
        call.enqueue(new Callback<DataPackage<List<PatronPatente>>>() {
            @Override
            public void onResponse(Call<DataPackage<List<PatronPatente>>> call, Response<DataPackage<List<PatronPatente>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PatronesPatentesService patronesPatentesService = new PatronesPatentesService(context);

                    List<PatronPatente> patronesNuevos = response.body().getData();
                    patronesPatentesService.deleteAll();
                    for(PatronPatente patronPatente : patronesNuevos) {
                        PatronPatente nuevoPatronPatente = new PatronPatente();
                        nuevoPatronPatente.setExpresionRegularPatente(patronPatente.getExpresionRegularPatente());
                        patronesPatentesService.save(nuevoPatronPatente);
                    }

                    Toast.makeText(context, "Registros recibidos exitosamente. Total patrones: " + patronesPatentesService.findAll().size(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Error al recibir registro: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataPackage<List<PatronPatente>>> call, Throwable t) {
                Log.e("DataError", "Fallo en la solicitud: " + t.getMessage());
                Toast.makeText(context, "Error al recibir registro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void actualizarPoligonosDeEstacionamiento(){
        Call<DataPackage<List<PoligonoDTO>>> call = apiService.obtenerPoligonos();
        call.enqueue(new Callback<DataPackage<List<PoligonoDTO>>>() {
            @Override
            public void onResponse(Call<DataPackage<List<PoligonoDTO>>> call, Response<DataPackage<List<PoligonoDTO>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PoligonoService poligonoService = new PoligonoService(context);
                    LineaPoligonoService lineaPoligonoService = new LineaPoligonoService(context);
                    PuntoService puntoService = new PuntoService(context);

                    List<PoligonoDTO> poligonosNuevos = response.body().getData();

                    // Limpiar datos anteriores
                    poligonoService.deleteAll();
                    lineaPoligonoService.deleteAll();
                    puntoService.deleteAll();

                    for (PoligonoDTO poligonoDTO : poligonosNuevos) {
                        List<LineaPoligonoDTO> lineasDTO = (List<LineaPoligonoDTO>) poligonoDTO.getLineasPoligono();
                        List<LineaPoligono> nuevasLineasPoligono = new ArrayList<>();

                        for (LineaPoligonoDTO lineaDTO : lineasDTO) {
                            // Guardar puntos y obtener sus IDs
                            PuntoDTO punto1DTO = lineaDTO.getPunto1();
                            PuntoDTO punto2DTO = lineaDTO.getPunto2();

                            Punto nuevoPunto1 = new Punto(punto1DTO.getId(), punto1DTO.getLatitud(), punto1DTO.getLongitud());
                            Punto nuevoPunto2 = new Punto(punto2DTO.getId(), punto2DTO.getLatitud(), punto2DTO.getLongitud());

                            // Guardar los puntos y obtener sus IDs
                            int idPunto1 = puntoService.save(nuevoPunto1);
                            int idPunto2 = puntoService.save(nuevoPunto2);

                            // Crear la nueva línea de polígono
                            LineaPoligono nuevaLineaPoligono = new LineaPoligono();
                            nuevaLineaPoligono.setPuntoInicioId(idPunto1);
                            nuevaLineaPoligono.setPuntoFinId(idPunto2);
                            nuevasLineasPoligono.add(nuevaLineaPoligono);
                        }

                        // Crear y guardar el nuevo polígono
                        Poligono nuevoPoligono = new Poligono();
                        nuevoPoligono.setId(poligonoDTO.getId());
                        nuevoPoligono.setPrecio(poligonoDTO.getPrecio());
                        nuevoPoligono.setNombre(poligonoDTO.getNombre());
                        poligonoService.save(nuevoPoligono);

                        for (LineaPoligono lineaPoligono : nuevasLineasPoligono){
                            lineaPoligono.setPoligonoId(nuevoPoligono.getId());
                            lineaPoligonoService.save(lineaPoligono);
                        }
                    }

                    Toast.makeText(context, "Registros recibidos exitosamente. Total poligonos: " + poligonoService.findAll().size(), Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Registros recibidos exitosamente. Total lineas: " + lineaPoligonoService.findAll().size(), Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Registros recibidos exitosamente. Total puntos: " + puntoService.findAll().size(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Error al recibir registro: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataPackage<List<PoligonoDTO>>> call, Throwable t) {
                Log.e("DataError", "Fallo en la solicitud: " + t.getMessage());
                Toast.makeText(context, "Error al recibir registro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}


