package com.example.conductor_app.frontend;

import android.content.Context;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout;

import com.example.conductor_app.backend.modelo.Estacionamiento;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EstacionamientoAdapter {
    private final Context context;
    private final List<Estacionamiento> estacionamientos;
    private final TableLayout tablaEstacionamientos;
    private final SimpleDateFormat sdf;

    public EstacionamientoAdapter(Context context, List<Estacionamiento> estacionamientos, TableLayout tablaEstacionamientos) {
        this.context = context;
        this.estacionamientos = estacionamientos;
        this.tablaEstacionamientos = tablaEstacionamientos;
        this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
    }

    public void updateTable() {
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

        TextView id = new TextView(context);
        id.setText(String.valueOf(estacionamiento.getId()));
        id.setPadding(16, 16, 16, 16);
        id.setGravity(android.view.Gravity.CENTER);
        id.setTextColor(android.graphics.Color.BLACK);
        row.addView(id);

        TextView horaInicio = new TextView(context);
        horaInicio.setText(sdf.format(estacionamiento.getHoraInicio()));
        horaInicio.setPadding(16, 16, 16, 16);
        horaInicio.setGravity(android.view.Gravity.CENTER);
        horaInicio.setTextColor(android.graphics.Color.BLACK);
        row.addView(horaInicio);

        TextView horaFin = new TextView(context);
        String horaFinTexto = estacionamiento.getHoraFin() != null
                ? sdf.format(estacionamiento.getHoraFin())
                : "N/A";
        horaFin.setText(horaFinTexto);
        horaFin.setPadding(16, 16, 16, 16);
        horaFin.setGravity(android.view.Gravity.CENTER);
        horaFin.setTextColor(android.graphics.Color.BLACK);
        row.addView(horaFin);

        // Añadir separador
        View separator = new View(context);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(android.graphics.Color.LTGRAY); // Cambia el color si es necesario
        tablaEstacionamientos.addView(separator); // Añadir separador después de la fila

        return row;
    }
}
