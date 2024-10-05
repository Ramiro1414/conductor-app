package com.example.conductor_app.frontend;

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

                refreshTableAndButtons(inicioEstacionamiento, finalizarEstacionamiento);
                Toast.makeText(this, "Estacionamiento iniciado para la patente: " + patenteSeleccionada, Toast.LENGTH_LONG).show();
            });
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
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

        builder.create().show();
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
