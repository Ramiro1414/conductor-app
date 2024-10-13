package com.example.conductor_app.frontend;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout;

import com.example.conductor_app.backend.Service.EstacionamientoService;
import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class EstacionamientoAdapter {
    private final Context context;
    private final List<Estacionamiento> estacionamientos;
    private final TableLayout tablaEstacionamientos;
    private final SimpleDateFormat sdf;
    private EstacionamientoService estacionamientoService;

    public EstacionamientoAdapter(Context context, List<Estacionamiento> estacionamientos, TableLayout tablaEstacionamientos) {
        this.context = context;
        this.estacionamientoService = new EstacionamientoService(this.context);
        this.estacionamientos = estacionamientos;
        this.tablaEstacionamientos = tablaEstacionamientos;
        this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
    }

    public void updateTable() {

        estacionamientos.sort((e1, e2) -> e2.getHoraInicio().compareTo(e1.getHoraInicio()));

        // Limpiar la tabla antes de agregar nuevos datos
        tablaEstacionamientos.removeAllViews();

        // Agregar cada estacionamiento como una fila en la tabla
        for (Estacionamiento estacionamiento : estacionamientos) {
            TableRow row = createRow(estacionamiento);
            tablaEstacionamientos.addView(row);
        }
    }

    private TableRow createRow(Estacionamiento estacionamiento) {
        TableRow row = new TableRow(context);

        // Establecer el fondo blanco y el texto negro
        row.setBackgroundColor(android.graphics.Color.WHITE);

        TextView patente = new TextView(context);
        patente.setText(estacionamiento.getPatenteCaracteres());
        patente.setPadding(16, 16, 16, 16);
        patente.setGravity(android.view.Gravity.CENTER);
        patente.setTextColor(android.graphics.Color.BLACK);
        row.addView(patente);

        TextView horaInicio = new TextView(context);
        horaInicio.setText(sdf.format(estacionamiento.getHoraInicio()));
        horaInicio.setPadding(16, 16, 16, 16);
        horaInicio.setGravity(android.view.Gravity.CENTER);
        horaInicio.setTextColor(android.graphics.Color.BLACK);
        row.addView(horaInicio);

        TextView horaFin = new TextView(context);
        String horaFinTexto = estacionamiento.getHoraFin() != null
                ? sdf.format(estacionamiento.getHoraFin())
                : "No finalizado";
        horaFin.setText(horaFinTexto);
        horaFin.setPadding(16, 16, 16, 16);
        horaFin.setGravity(android.view.Gravity.CENTER);
        horaFin.setTextColor(android.graphics.Color.BLACK);
        row.addView(horaFin);

        // Si horaFin es null, agregar botón de finalizar
        if (estacionamiento.getHoraFin() == null) {
            Button finalizarButton = new Button(context);
            finalizarButton.setText("Finalizar");

            // Establecer colores
            finalizarButton.setTextColor(android.graphics.Color.WHITE); // Texto en blanco

            // Establecer márgenes y padding
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(16, 16, 16, 16);  // Márgenes (arriba, izquierda, derecha, abajo)
            finalizarButton.setLayoutParams(params);

            // Agregar borde al botón (si quieres hacerlo programáticamente)
            finalizarButton.setBackgroundResource(R.drawable.button_background); // Suponiendo que tienes un drawable
            finalizarButton.setPadding(20, 10, 20, 10);  // Padding del botón

            finalizarButton.setOnClickListener(v -> {
                // Finalizar el estacionamiento
                estacionamiento.setHoraFin(System.currentTimeMillis());
                estacionamientoService.update(estacionamiento);  // Asegúrate de tener acceso al service

                // Refrescar la tabla después de finalizar
                updateTable();
            });
            row.addView(finalizarButton);
        }

        // Añadir separador
        View separator = new View(context);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(android.graphics.Color.LTGRAY);
        tablaEstacionamientos.addView(separator); // Añadir separador después de la fila

        return row;
    }

}
