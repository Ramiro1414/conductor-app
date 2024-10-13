package com.example.conductor_app.backend.httpServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.conductor_app.backend.Service.PatronesPatentesService;
import com.example.conductor_app.backend.modelo.PatronPatente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PruebaObtenerDatos {
    private static final String BASE_URL = "http://if012estm.fi.mdn.unp.edu.ar:28003/";
    private final ApiService apiService;
    private final Context context;

    public PruebaObtenerDatos(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

//    public void obtenerDatosDelServidor() {
//        Call<DataPackage<Object>> call = apiService.obtenerDatos();
//        call.enqueue(new Callback<DataPackage<Object>>() {
//            @Override
//            public void onResponse(Call<DataPackage<Object>>  call, Response<DataPackage<Object>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(context, "Registro recibido exitosamente:\n" + response.body().getData().toString(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Error al recibir registro: " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<DataPackage<Object>> call, Throwable t) {
//                Log.e("DataError", "Fallo en la solicitud: " + t.getMessage());
//                Toast.makeText(context, "Error al recibir registro: " + t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public void obtenerPatronesDePatentes() {
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

                    Toast.makeText(context, "Registros recibidos exitosamente. Registros obtenidos: " + patronesNuevos.size(), Toast.LENGTH_LONG).show();

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
}


