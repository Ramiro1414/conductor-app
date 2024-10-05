package com.example.conductor_app.frontend;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.backend.Service.EstacionamientoService;
import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.conductor_app.backend.modelo.Patente;
import com.example.myapplication.R;

public class RegistrarEstacionamientoActivity extends AppCompatActivity {

    private EstacionamientoService estacionamientoService;
    private EstacionamientoAdapter estacionamientoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_estacionamiento);

        this.estacionamientoService = new EstacionamientoService(this);
        Button inicioEstacionamiento = findViewById(R.id.inicarEstacionamiento);
        Button finalizarEstacionamiento = findViewById(R.id.finalizarEstacionamiento);
        TableLayout tablaEstacionamientos = findViewById(R.id.tablaEstacionamientos);

        // Inicializar el adaptador y cargar la tabla al inicio
        estacionamientoAdapter = new EstacionamientoAdapter(this, estacionamientoService.findAll(), tablaEstacionamientos);
        estacionamientoAdapter.updateTable();

        updateButtonState(inicioEstacionamiento, finalizarEstacionamiento);

        inicioEstacionamiento.setOnClickListener(v -> {
            Estacionamiento nuevoEstacionamiento = new Estacionamiento();
            PatenteService patenteService = new PatenteService(this);
            Patente patente = patenteService.getPatenteByCaracteres("aaa111");
            nuevoEstacionamiento.setPatenteId(patente.getId());
            nuevoEstacionamiento.setHoraInicio(System.currentTimeMillis());
            estacionamientoService.save(nuevoEstacionamiento);

            refreshTableAndButtons(inicioEstacionamiento, finalizarEstacionamiento);
        });

        finalizarEstacionamiento.setOnClickListener(v -> {
            Estacionamiento nuevoEstacionamiento = estacionamientoService.findUncompleted();
            if (nuevoEstacionamiento == null)
                return;

            nuevoEstacionamiento.setHoraFin(System.currentTimeMillis());
            estacionamientoService.update(nuevoEstacionamiento);

            refreshTableAndButtons(inicioEstacionamiento, finalizarEstacionamiento);
        });
    }

    private void refreshTableAndButtons(Button inicioEstacionamiento, Button finalizarEstacionamiento) {
        // Actualizar la lista de estacionamientos y la tabla
        estacionamientoAdapter = new EstacionamientoAdapter(this, estacionamientoService.findAll(), findViewById(R.id.tablaEstacionamientos));
        estacionamientoAdapter.updateTable();

        updateButtonState(inicioEstacionamiento, finalizarEstacionamiento);
    }

    private void updateButtonState(Button inicioEstacionamiento, Button finalizarEstacionamiento) {
        if (estacionamientoService.findUncompleted() == null) {
            inicioEstacionamiento.setEnabled(true);
            finalizarEstacionamiento.setEnabled(false);
        } else {
            inicioEstacionamiento.setEnabled(false);
            finalizarEstacionamiento.setEnabled(true);
        }
    }
}
