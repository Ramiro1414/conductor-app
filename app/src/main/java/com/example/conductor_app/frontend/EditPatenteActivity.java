package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.conductor_app.backend.Service.PatenteRepetidaException;
import com.example.conductor_app.backend.Service.PatenteService;
import com.example.conductor_app.backend.modelo.Patente;
import com.example.myapplication.R;

public class EditPatenteActivity extends AppCompatActivity {

    private PatenteService patenteService;
    private EditText editTextPatente;
    private TextView textViewOldPatente; // Agrega este TextView
    private Button buttonSave;
    private int patenteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patente);

        patenteService = new PatenteService(this);
        editTextPatente = findViewById(R.id.editTextEditPatente);
        textViewOldPatente = findViewById(R.id.textViewOldPatente); // Encuentra el TextView
        buttonSave = findViewById(R.id.buttonSavePatente);

        // Obtener los datos del Intent
        Intent intent = getIntent();
        patenteId = intent.getIntExtra("patente_id", -1);
        String patenteTexto = intent.getStringExtra("patente_texto");
        if (patenteId == -1 || patenteTexto == null) {
            Toast.makeText(this, "Datos de patente inválidos", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si los datos no son válidos
        } else {
            textViewOldPatente.setText(patenteTexto); // Configura el TextView
            editTextPatente.setText(patenteTexto); // Configura el EditText
        }

        buttonSave.setOnClickListener(v -> savePatente());
    }

    private void savePatente() {
        String newPatenteText = editTextPatente.getText().toString().trim();
        if(newPatenteText.isEmpty()){
            Toast.makeText(this, "Ingrese un texto válido", Toast.LENGTH_SHORT).show();
            return;
        }

        Patente patente = new Patente();
        patente.setId(patenteId);
        patente.setCaracteres(newPatenteText);

        try{
            patenteService.updatePatente(patente);
        } catch (PatenteRepetidaException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Patente actualizada", Toast.LENGTH_SHORT).show();
        finish(); // Cierra la actividad y regresa a la anterior
    }
}
