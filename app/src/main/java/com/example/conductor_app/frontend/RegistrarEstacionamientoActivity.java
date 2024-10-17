package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.backend.Service.EstacionamientoService;
import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.conductor_app.backend.modelo.Patente;
import com.example.myapplication.R;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrarEstacionamientoActivity extends AppCompatActivity {

    private EstacionamientoService estacionamientoService;
    private EstacionamientoActivoAdapter estacionamientoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_estacionamiento);

        this.estacionamientoService = new EstacionamientoService(this);
        Button inicioEstacionamiento = findViewById(R.id.inicarEstacionamiento);
        TableLayout tablaEstacionamientos = findViewById(R.id.tablaEstacionamientos);


        estacionamientoAdapter = new EstacionamientoActivoAdapter(this, tablaEstacionamientos);
        estacionamientoAdapter.updateTable(estacionamientoService.findUncompleted());

        updateButtonState(inicioEstacionamiento);

        inicioEstacionamiento.setOnClickListener(v -> {
            showPatenteDialog((dialog, which) -> {
                // Obtener la patente seleccionada por índice
                PatenteService patenteService = new PatenteService(this);
                List<Patente> patentes = patenteService.getAllPatentes();
                String patenteSeleccionada = patentes.get(which).getCaracteres(); // Obtener caracteres de la patente seleccionada

                // Crear nuevo estacionamiento
                Estacionamiento nuevoEstacionamiento = new Estacionamiento();
                nuevoEstacionamiento.setPatenteCaracteres(patenteSeleccionada);
                nuevoEstacionamiento.setHoraInicio(System.currentTimeMillis());
                estacionamientoService.save(nuevoEstacionamiento);

                refreshTableAndButtons(inicioEstacionamiento);
                Toast.makeText(this, "Estacionamiento iniciado para la patente: " + patenteSeleccionada, Toast.LENGTH_LONG).show();
            });
        });

    }

    private void showPatenteDialog(DialogInterface.OnClickListener patenteSeleccionadaListener) {
        PatenteService patenteService = new PatenteService(this);
        List<Patente> patentes = patenteService.getAllPatentes();
        String[] patenteCaracteresArray = new String[patentes.size()];

        for (int i = 0; i < patentes.size(); i++) {
            patenteCaracteresArray[i] = patentes.get(i).getCaracteres();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Patente")
                .setItems(patenteCaracteresArray, patenteSeleccionadaListener)
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss())
                .setNeutralButton("Crear nueva patente", (dialog, id) -> {
                    // Abrir la actividad para crear una nueva patente
                    Intent intent = new Intent(RegistrarEstacionamientoActivity.this, CRUD_Patentes_Activity.class);
                    startActivity(intent);
                });

        builder.create().show();
    }

    private void refreshTableAndButtons(Button inicioEstacionamiento) {
        List<Estacionamiento> todosEstacionamientos = estacionamientoService.findAll();

        List<Estacionamiento> estacionamientosNoFinalizados = todosEstacionamientos.stream()
                .filter(est -> est.getHoraFin() == null)
                .collect(Collectors.toList());

        // Actualizar la tabla con solo los estacionamientos no finalizados
        estacionamientoAdapter = new EstacionamientoActivoAdapter(this, findViewById(R.id.tablaEstacionamientos));
        estacionamientoAdapter.updateTable(estacionamientosNoFinalizados);

        // Actualizar el estado del botón
        updateButtonState(inicioEstacionamiento);
    }


    private void updateButtonState(Button inicioEstacionamiento) {
        inicioEstacionamiento.setEnabled(true);
    }

}
