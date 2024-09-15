package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.Patente;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CRUD_Patentes_Activity extends AppCompatActivity {

    private PatenteService patenteService;
    private EditText editTextNewPatente;
    private ListView listViewPatentes;
    private PatenteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crud_patentes_layout);

        patenteService = new PatenteService(this, "myDB", null, 1);
        editTextNewPatente = findViewById(R.id.editTextNewPatente);
        Button buttonAddPatente = findViewById(R.id.buttonAddPatente);
        listViewPatentes = findViewById(R.id.listViewPatentes);

        // Inicializar el adaptador con una lista vacía
        adapter = new PatenteAdapter(this, new ArrayList<>());
        listViewPatentes.setAdapter(adapter);

        // Configurar el botón para agregar una nueva patente
        buttonAddPatente.setOnClickListener(v -> addPatente());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualiza la lista cada vez que se reanuda la actividad
        actualizarListaDePatentes();
    }

    public void actualizarListaDePatentes() {
        // Actualiza la lista de patentes en el frontend
        List<Patente> patentes = patenteService.findAll();
        adapter.clear(); // Limpiar el adaptador antes de agregar nuevos datos
        adapter.addAll(patentes); // Agregar todas las patentes al adaptador
        adapter.notifyDataSetChanged(); // Notificar al adaptador de los cambios
    }

    private void addPatente() {
        String patenteTexto = editTextNewPatente.getText().toString().trim();
        if (!patenteTexto.isEmpty()) {
            Patente patente = new Patente();
            patente.setCaracteres(patenteTexto);
            patenteService.save(patente);
            editTextNewPatente.setText("");
            actualizarListaDePatentes();
            Toast.makeText(this, "Patente agregada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ingrese una patente", Toast.LENGTH_SHORT).show();
        }
    }

    public void showEditPatenteActivity(Patente patente) {
        Intent intent = new Intent(this, EditPatenteActivity.class);
        intent.putExtra("patente_id", patente.getId());
        intent.putExtra("patente_texto", patente.getCaracteres());
        startActivity(intent);
    }
}
