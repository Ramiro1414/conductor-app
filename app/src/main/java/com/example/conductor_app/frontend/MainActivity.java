package com.example.conductor_app.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGoToCRUD = findViewById(R.id.buttonGoToCRUD);
        Button buttonCoords = findViewById(R.id.buttonCoords);

        buttonGoToCRUD.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CRUD_Patentes_Activity.class);
            startActivity(intent);
        });

        buttonCoords.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CRUD_Patentes_Activity.class);
            startActivity(intent);
        });
    }

}
