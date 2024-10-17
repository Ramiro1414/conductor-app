package com.example.conductor_app.frontend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EstacionamientoTransaccionesAdapter {

    private final Context context;
    private final TableLayout tablaTransacciones;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public EstacionamientoTransaccionesAdapter(Context context, TableLayout tablaTransacciones) {
        this.context = context;
        this.tablaTransacciones = tablaTransacciones;
    }

    public void updateTable(List<Estacionamiento> estacionamientos) {
        tablaTransacciones.removeAllViews();

        estacionamientos.sort((e1, e2) -> e2.getHoraInicio().compareTo(e1.getHoraInicio()));

        for (Estacionamiento estacionamiento : estacionamientos) {
            TableRow row = createRow(estacionamiento);
            tablaTransacciones.addView(row);
        }
    }

    private TableRow createRow(Estacionamiento estacionamiento) {
        TableRow row = new TableRow(context);
        row.setPadding(0, 25, 0, 25); // Padding alrededor de la fila

        boolean noFinalizado = estacionamiento.getHoraFin() == null;

        // Cambiar a color resaltado si no está finalizado
        if (noFinalizado) {
            row.setBackgroundColor(context.getResources().getColor(R.color.resaltado)); // Color resaltado para estacionamientos no finalizados
        } else {
            row.setBackgroundColor(Color.WHITE); // Color normal
        }

        // Aumentar la altura de la fila
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                (int) context.getResources().getDimension(R.dimen.row_height) // Puedes definir esto en dimens.xml
        );
        row.setLayoutParams(params);

        // Crear el SpannableString para Patente y Fecha
        String patenteText = estacionamiento.getPatenteCaracteres();
        String fechaText = sdf.format(estacionamiento.getHoraInicio());

        SpannableString spannableString = new SpannableString(patenteText + "\n" + fechaText);

        // Establecer tamaño de fuente para la patente
        spannableString.setSpan(new RelativeSizeSpan(1.0f), 0, patenteText.length(), 0); // Patente a tamaño normal (18sp)

        // Establecer tamaño de fuente para la fecha
        spannableString.setSpan(new RelativeSizeSpan(0.8f), patenteText.length() + 1, spannableString.length(), 0); // Fecha más pequeña (16sp)

        // TextView para Patente y Fecha
        TextView patenteFecha = new TextView(context);
        patenteFecha.setText(spannableString);
        patenteFecha.setPadding(40, 16, 0, 16); // Ajustar padding para no estar muy pegado al borde
        patenteFecha.setGravity(Gravity.CENTER_VERTICAL);
        patenteFecha.setTextColor(Color.BLACK);
        patenteFecha.setTextSize(18); // Tamaño de fuente para Patente
        row.addView(patenteFecha);

        // TextView para Tiempo Estacionado
        TextView duracion = new TextView(context);
        if (noFinalizado) {
            duracion.setText("-"); // Texto si no está finalizado
        } else {
            duracion.setText(calcularTiempoEstacionado(estacionamiento));
        }
        duracion.setPadding(0, 16, 16, 16); // Ajustar padding para no estar muy pegado al borde
        duracion.setGravity(Gravity.CENTER);
        duracion.setTextColor(Color.BLACK);
        duracion.setTextSize(18); // Tamaño de fuente para Duración
        row.addView(duracion);

        // TextView para Saldo Gastado
        TextView saldo = new TextView(context);
        saldo.setText("0$"); // Saldo hardcoded a 0
        saldo.setPadding(16, 16, 40, 16); // Ajustar padding para no estar muy pegado al borde
        saldo.setGravity(Gravity.CENTER_VERTICAL);
        saldo.setTextColor(Color.BLACK);
        saldo.setTextSize(18); // Tamaño de fuente para Saldo
        row.addView(saldo);

        // Configura el click listener para la fila
        row.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleEstacionamientoActivity.class);
            intent.putExtra("patente", estacionamiento.getPatenteCaracteres());
            intent.putExtra("horaInicio", estacionamiento.getHoraInicio());
            intent.putExtra("horaFin", estacionamiento.getHoraFin());
            intent.putExtra("tiempoEstacionado", calcularTiempoEstacionado(estacionamiento));
            context.startActivity(intent);
        });

        // Agregar OnTouchListener para animaciones al tocar
        row.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Cambiar el fondo y aplicar la animación de escala al presionar
                    row.setBackgroundColor(Color.LTGRAY);
                    row.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    // Restaurar el color de fondo y la escala
                    row.setBackgroundColor(noFinalizado ? context.getResources().getColor(R.color.resaltado) : Color.WHITE);
                    row.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
            }
            return false; // Devuelve false para permitir que el evento de clic funcione
        });

        View separator = new View(context);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(Color.LTGRAY);
        tablaTransacciones.addView(separator);

        return row;
    }


    private String calcularTiempoEstacionado(Estacionamiento estacionamiento) {
        long tiempo = (estacionamiento.getHoraFin() != null ? estacionamiento.getHoraFin() : System.currentTimeMillis()) - estacionamiento.getHoraInicio();
        long horas = (tiempo / (1000 * 60 * 60)) % 24;
        long minutos = (tiempo / (1000 * 60)) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", horas, minutos);
    }
}
