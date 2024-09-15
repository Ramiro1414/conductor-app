package com.example.conductor_app.backend.Service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CoordsService {

    private final FusedLocationProviderClient fusedLocationClient;

    public CoordsService(Context context) {
        // Inicializa el fusedLocationClient para obtener acceso al servicio de ubicación de Google.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(OnSuccessListener<Location> listener) {
        // Este método se encarga de solicitar la última ubicación del dispositivo.
        // El método addOnSuccessListener recibe un listener (un objeto que "escucha" el resultado).
        fusedLocationClient.getLastLocation().addOnSuccessListener(listener);
    }
}
