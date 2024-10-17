package com.example.conductor_app.frontend;

import android.os.Bundle;
import android.widget.TableLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.backend.Service.EstacionamientoService;
import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.myapplication.R;

import java.util.List;

public class HistorialTransaccionesActivity extends AppCompatActivity {

    private EstacionamientoService estacionamientoService;
    private EstacionamientoTransaccionesAdapter estacionamientoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_transacciones);

        this.estacionamientoService = new EstacionamientoService(this);
        TableLayout tablaEstacionamientos = findViewById(R.id.tablaEstacionamientosHistorial);

        // Obtener todos los estacionamientos
        List<Estacionamiento> todosLosEstacionamientos = estacionamientoService.findAll();

        // Inicializar el adaptador con todos los estacionamientos sin botón de finalizar
        estacionamientoAdapter = new EstacionamientoTransaccionesAdapter(this, tablaEstacionamientos); // false para no mostrar el botón
        estacionamientoAdapter.updateTable(todosLosEstacionamientos);
    }
}
