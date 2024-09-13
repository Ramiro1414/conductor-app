package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CRUD_patentes extends AppCompatActivity {

    private VehicleDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private VehicleAdapter vehicleAdapter;
    private List<String> vehicleList;

    private static final int EDIT_VEHICLE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_patentes);

        dbHelper = new VehicleDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewVehicles);
        vehicleList = new ArrayList<>();

        vehicleAdapter = new VehicleAdapter(vehicleList, new VehicleAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(String oldLicensePlate, int position) {
                Intent intent = new Intent(CRUD_patentes.this, EditVehicleActivity.class);
                intent.putExtra("OLD_PATENTE", oldLicensePlate);
                intent.putExtra("POSITION", position);
                startActivityForResult(intent, EDIT_VEHICLE_REQUEST_CODE);
            }

            @Override
            public void onDeleteClick(String licensePlate, int position) {
                deleteVehicle(licensePlate, position);
            }
        }, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(vehicleAdapter);

        EditText editTextLicensePlate = findViewById(R.id.editTextLicensePlate);
        findViewById(R.id.buttonAddVehicle).setOnClickListener(v -> {
            String licensePlate = editTextLicensePlate.getText().toString().trim();

            if (!licensePlate.isEmpty()) {
                addVehicle(licensePlate);
                Toast.makeText(this, "Vehículo agregado", Toast.LENGTH_SHORT).show();
                editTextLicensePlate.setText("");
                loadVehicles();
            } else {
                Toast.makeText(this, "Ingrese una patente", Toast.LENGTH_SHORT).show();
            }
        });

        loadVehicles();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_VEHICLE_REQUEST_CODE && resultCode == RESULT_OK) {
            String oldLicensePlate = data.getStringExtra("OLD_PATENTE");
            String newLicensePlate = data.getStringExtra("NEW_PATENTE");
            int position = data.getIntExtra("POSITION", -1);

            if (position >= 0) {
                updateVehicle(oldLicensePlate, newLicensePlate, position);
            }
        }
    }

    public void addVehicle(String licensePlate) {
        ContentValues values = new ContentValues();
        values.put(VehicleDatabaseHelper.COLUMN_LICENSE_PLATE, licensePlate);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(VehicleDatabaseHelper.TABLE_VEHICLES, null, values);
        db.close();
    }

    public void loadVehicles() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                VehicleDatabaseHelper.COLUMN_LICENSE_PLATE
        };

        Cursor cursor = db.query(
                VehicleDatabaseHelper.TABLE_VEHICLES,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        vehicleList.clear();
        while (cursor.moveToNext()) {
            String licensePlate = cursor.getString(
                    cursor.getColumnIndexOrThrow(VehicleDatabaseHelper.COLUMN_LICENSE_PLATE));
            vehicleList.add(licensePlate);
        }
        cursor.close();
        db.close();

        vehicleAdapter.notifyDataSetChanged();
    }

    private void deleteVehicle(String licensePlate, int position) {
        dbHelper.deleteVehicle(licensePlate);
        vehicleList.remove(position);
        vehicleAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Vehículo eliminado", Toast.LENGTH_SHORT).show();
    }

    private void updateVehicle(String oldLicensePlate, String newLicensePlate, int position) {
        dbHelper.updateVehicle(oldLicensePlate, newLicensePlate);
        vehicleList.set(position, newLicensePlate);
        vehicleAdapter.notifyItemChanged(position);
        Toast.makeText(this, "Vehículo actualizado", Toast.LENGTH_SHORT).show();
    }
}
