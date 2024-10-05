package com.example.conductor_app.backend.httpServices;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.RegistroConductor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PruebaEnviarDatos {
    private static final String BASE_URL = "http://if012estm.fi.mdn.unp.edu.ar:28003/";
    private ApiServicePrueba apiService;
    private Context context;

    public PruebaEnviarDatos(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiServicePrueba.class);
    }

    public void enviarRegistroConductor() {
        PatenteService patenteService = new PatenteService(context);
        String patenteCaracteres = patenteService.getAllPatentes().get(0).getCaracteres();

        // Obtener la hora local del dispositivo
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Timestamp horaInicio = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.SECOND, 10);
        Timestamp horaFin = new Timestamp(calendar.getTimeInMillis());

        RegistroConductor registroConductor = new RegistroConductor(patenteCaracteres, horaInicio, horaFin);
        registroConductor.setId(0);

        // Llama al servicio y maneja la respuesta
        apiService.registrarDatos(registroConductor).enqueue(new Callback<DataPackage<Object>>() {
            @Override
            public void onResponse(Call<DataPackage<Object>> call, Response<DataPackage<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Registro enviado exitosamente:\n" + response.body().getData().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al enviar registro: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataPackage<Object>> call, Throwable t) {
                Log.e("DataError", "Fallo en la solicitud: " + t.getMessage());
                Toast.makeText(context, "Error al enviar registro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}