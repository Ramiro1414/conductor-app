package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.conductor_app.backend.Service.CoordsManager;
import com.example.conductor_app.backend.Service.CoordsService;
import com.example.conductor_app.backend.httpServices.PruebaEnviarDatos;
import com.example.conductor_app.backend.httpServices.PruebaObtenerDatos;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    CoordsManager coordsManager;

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
        Button buttonPruebaData = findViewById(R.id.buttonPruebaData);
        Button buttonPruebaSendData = findViewById(R.id.buttonPruebaSendData);
        Button buttonGoToEstacionamiento = findViewById(R.id.buttonGoToEstacionar);
        Button buttonObtenerPatrones = findViewById(R.id.buttonObtenerPatrones);

        buttonGoToCRUD.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CRUD_Patentes_Activity.class);
            startActivity(intent);
        });

        buttonCoords.setOnClickListener(v -> {
            coordsManager.requestCoordinates();
        });

        buttonPruebaData.setOnClickListener(v -> {
            PruebaObtenerDatos dataGetter = new PruebaObtenerDatos(this);
            dataGetter.obtenerDatosDelServidor();
        });

        buttonPruebaSendData.setOnClickListener(v -> {
            PruebaEnviarDatos dataSender = new PruebaEnviarDatos(this);
            dataSender.enviarRegistroConductor();
        });

        buttonGoToEstacionamiento.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrarEstacionamientoActivity.class);
            startActivity(intent);
        });

        buttonObtenerPatrones.setOnClickListener(v -> {
            PruebaObtenerDatos dataGetter = new PruebaObtenerDatos(this);
            dataGetter.obtenerPatronesDePatentes();
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
