package com.example.conductor_app.frontend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.conductor_app.backend.Service.EstacionamientoService;
import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EstacionamientoActivoAdapter {

    private final Context context;
    private final TableLayout tablaEstacionamientos;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss", Locale.getDefault());
    private final EstacionamientoService estacionamientoService;

    public EstacionamientoActivoAdapter(Context context, TableLayout tablaEstacionamientos) {
        this.context = context;
        this.tablaEstacionamientos = tablaEstacionamientos;
        this.estacionamientoService = new EstacionamientoService(context);
    }

    public void updateTable(List<Estacionamiento> estacionamientos) {
        tablaEstacionamientos.removeAllViews();

        View tableHeader = ((Activity)context).findViewById(R.id.table_header_row);
        if (estacionamientos.isEmpty()) {
            tableHeader.setVisibility(View.GONE); // Ocultar encabezado si no hay estacionamientos
        } else {
            tableHeader.setVisibility(View.VISIBLE); // Mostrar encabezado si hay al menos uno
            estacionamientos.sort((e1, e2) -> e2.getHoraInicio().compareTo(e1.getHoraInicio()));

            for (Estacionamiento estacionamiento : estacionamientos) {
                if (estacionamiento.getHoraFin() == null) {
                    TableRow row = createRow(estacionamiento);
                    tablaEstacionamientos.addView(row);
                }
            }
        }
    }

    private TableRow createRow(Estacionamiento estacionamiento) {
        TableRow row = new TableRow(context);
        row.setBackgroundColor(Color.WHITE);

        // Patente
        TextView patente = new TextView(context);
        patente.setText(estacionamiento.getPatenteCaracteres());
        setTextViewStyle(patente);
        patente.setPadding(16, 16, 16, 16);
        patente.setGravity(android.view.Gravity.CENTER);
        patente.setTextColor(android.graphics.Color.BLACK);
        row.addView(patente);

        // Hora Inicio
        TextView horaInicio = new TextView(context);
        horaInicio.setText(sdf.format(estacionamiento.getHoraInicio()));
        setTextViewStyle(horaInicio);
        horaInicio.setPadding(16, 16, 16, 16);
        horaInicio.setGravity(android.view.Gravity.CENTER);
        horaInicio.setTextColor(android.graphics.Color.BLACK);
        row.addView(horaInicio);

        // Botón de Finalizar
        Button finalizarButton = new Button(context);
        finalizarButton.setText("Finalizar");
        finalizarButton.setBackgroundResource(R.drawable.button_background);

        // Establecer márgenes y padding
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 16);  // Márgenes (arriba, izquierda, derecha, abajo)
        finalizarButton.setLayoutParams(params);
        finalizarButton.setPadding(20, 10, 20, 10);  // Padding del botón


        finalizarButton.setOnClickListener(v -> {
            estacionamiento.setHoraFin(System.currentTimeMillis());
            estacionamientoService.update(estacionamiento);
            tablaEstacionamientos.removeView(row); // Remueve la fila

            // Actualiza la tabla para ocultar el encabezado si no hay más estacionamientos
            updateTable(estacionamientoService.findUncompleted()); // Reemplaza este método con el que uses para obtener la lista de estacionamientos activos
        });
        row.addView(finalizarButton);

        View separator = new View(context);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(android.graphics.Color.LTGRAY);
        tablaEstacionamientos.addView(separator);

        return row;
    }

    private void setTextViewStyle(TextView textView) {
        textView.setPadding(16, 16, 16, 16);
        textView.setTextColor(Color.BLACK);
    }
}
