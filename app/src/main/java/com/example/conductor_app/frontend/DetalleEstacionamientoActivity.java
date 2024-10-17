package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetalleEstacionamientoActivity extends AppCompatActivity {

    private TextView patenteValueTextView;
    private TextView horaInicioValueTextView;
    private TextView horaFinValueTextView;
    private TextView tiempoEstacionadoValueTextView;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_estacionamiento);

        // Inicializa los TextViews
        patenteValueTextView = findViewById(R.id.patenteValueTextView);
        horaInicioValueTextView = findViewById(R.id.horaInicioValueTextView);
        horaFinValueTextView = findViewById(R.id.horaFinValueTextView);
        tiempoEstacionadoValueTextView = findViewById(R.id.tiempoEstacionadoValueTextView);

        // Obtener los datos del Intent
        Intent intent = getIntent();
        String patente = intent.getStringExtra("patente");
        long horaInicio = intent.getLongExtra("horaInicio", 0);
        long horaFin = intent.getLongExtra("horaFin", 0);  // 0 si no hay fin
        String tiempoEstacionado = intent.getStringExtra("tiempoEstacionado");

        mostrarDetalles(patente, horaInicio, horaFin, tiempoEstacionado);
    }

    private void mostrarDetalles(String patente, long horaInicio, long horaFin, String tiempoEstacionado) {
        // Configurar los TextViews con los datos recibidos
        patenteValueTextView.setText(patente);

        horaInicioValueTextView.setText(sdf.format(horaInicio));

        String horaFinTexto = (horaFin != 0)
                ? sdf.format(horaFin)
                : "No finalizado";
        horaFinValueTextView.setText(horaFinTexto);

        tiempoEstacionadoValueTextView.setText(tiempoEstacionado);
    }

    private String calcularTiempoEstacionado(Estacionamiento estacionamiento) {
        long inicio = estacionamiento.getHoraInicio();
        long fin = estacionamiento.getHoraFin() != null ? estacionamiento.getHoraFin() : System.currentTimeMillis();

        long diffMillis = fin - inicio;
        long horas = (diffMillis / (1000 * 60 * 60)) % 24;
        long minutos = (diffMillis / (1000 * 60)) % 60;

        return String.format(Locale.getDefault(), "%02d horas %02d minutos", horas, minutos);
    }
}
