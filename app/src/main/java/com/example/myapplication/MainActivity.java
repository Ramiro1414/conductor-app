package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button miBoton = findViewById(R.id.miBoton);
        Button buttonGoToCRUD = findViewById(R.id.buttonGoToCRUD);

        miBoton.setOnClickListener(v -> {
            CharSequence text = "Hello toast!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(MainActivity.this, text, duration);
            toast.show();
        });

        buttonGoToCRUD.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CRUD_patentes.class);
            startActivity(intent);
        });
    }
}
