package com.example.conductor_app;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import com.example.conductor_app.backend.Service.CoordsService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class CoordsServiceTest {

    private CoordsService coordsService;
    private FusedLocationProviderClient fusedLocationClient;

    @Before
    public void setUp() {
        Context context = Robolectric.setupActivity(Activity.class);
        fusedLocationClient = mock(FusedLocationProviderClient.class);
        coordsService = new CoordsService(context, fusedLocationClient); // Inyección del mock
    }

    @Test
    public void testGetLastLocation() {
        // Crea un objeto Location simulado
        Location mockLocation = new Location("dummyprovider");
        mockLocation.setLatitude(40.7128);
        mockLocation.setLongitude(-74.0060);

        // Configura el comportamiento del mock para que devuelva un Task
        Task<Location> mockTask = mock(Task.class);
        when(fusedLocationClient.getLastLocation()).thenReturn(mockTask);
        when(mockTask.addOnSuccessListener(any())).thenAnswer(invocation -> {
            OnSuccessListener<Location> listener = invocation.getArgument(0);
            listener.onSuccess(mockLocation); // Simula la llamada al listener
            return mockTask;
        });

        // Ejecuta el método
        coordsService.getLastLocation(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Asegúrate de que la ubicación devuelta sea la esperada
                assertEquals(40.7128, location.getLatitude(), 0.0001);
                assertEquals(-74.0060, location.getLongitude(), 0.0001);
            }
        });
    }
}
