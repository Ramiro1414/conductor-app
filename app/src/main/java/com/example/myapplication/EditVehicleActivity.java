package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditVehicleActivity extends AppCompatActivity {

    private EditText editTextLicensePlate;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        editTextLicensePlate = findViewById(R.id.editTextLicensePlate);
        buttonSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        String oldLicensePlate = intent.getStringExtra("OLD_PATENTE");

        editTextLicensePlate.setText(oldLicensePlate);

        buttonSave.setOnClickListener(v -> {
            String newLicensePlate = editTextLicensePlate.getText().toString().trim();
            if (!newLicensePlate.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("OLD_PATENTE", oldLicensePlate);
                resultIntent.putExtra("NEW_PATENTE", newLicensePlate);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Ingrese una patente válida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
