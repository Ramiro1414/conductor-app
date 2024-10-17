package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.conductor_app.backend.Service.CoordsManager;
import com.example.conductor_app.backend.Service.CoordsService;
import com.example.conductor_app.backend.httpServices.ActualizacionService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private CoordsManager coordsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        renderTitulo();

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        CoordsService coordsService = new CoordsService(this, fusedLocationClient);
        coordsManager = new CoordsManager(this, coordsService);

        Button buttonGoToCRUD = findViewById(R.id.buttonGoToCRUD);
        Button buttonCoords = findViewById(R.id.buttonCoords);
        Button buttonGoToEstacionamiento = findViewById(R.id.buttonGoToEstacionar);
        Button buttonActualizar = findViewById(R.id.buttonActualziar);
        Button buttonTransacciones = findViewById(R.id.buttonTransacciones);

        buttonGoToCRUD.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CRUD_Patentes_Activity.class);
            startActivity(intent);
        });

        buttonCoords.setOnClickListener(v -> {
            coordsManager.requestCoordinates();
        });

        buttonGoToEstacionamiento.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrarEstacionamientoActivity.class);
            startActivity(intent);
        });

        buttonActualizar.setOnClickListener(v -> {
            ActualizacionService actualizacionService = new ActualizacionService(this);
            actualizacionService.actualizarPoligonosDeEstacionamiento();
            actualizacionService.actualizarPatronesDePatentes();
            actualizacionService.actualizarHorariosDeEstacionamiento();

            new DataBaseLogPrinter(this);
        });

        buttonTransacciones.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistorialTransaccionesActivity.class);
            startActivity(intent);
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        coordsManager.handlePermissionResult(requestCode, permissions, grantResults);
    }

    public void renderTitulo() {
        TextView textViewTitle = findViewById(R.id.textView3321);
        String text = "EstacionAR";
        SpannableString spannableString = new SpannableString(text);

        // Obtener el color cyan de los recursos
        int cyanColor = getResources().getColor(R.color.cyan, getTheme());

        // Cambiar el color de "AR" a cyan
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(cyanColor);
        spannableString.setSpan(colorSpan, 8, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewTitle.setText(spannableString);
    }
}
