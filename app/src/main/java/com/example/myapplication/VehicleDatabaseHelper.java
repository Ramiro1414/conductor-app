package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VehicleDatabaseHelper extends SQLiteOpenHelper {
    // Nombre de la base de datos y la tabla
    private static final String DATABASE_NAME = "vehicles.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_VEHICLES = "vehicles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LICENSE_PLATE = "license_plate";

    // Sentencia SQL para crear la tabla
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_VEHICLES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LICENSE_PLATE + " TEXT NOT NULL);";

    public VehicleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void deleteVehicle(String licensePlate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VEHICLES, COLUMN_LICENSE_PLATE + " = ?", new String[]{licensePlate});
        db.close();
    }

    public void updateVehicle(String oldLicensePlate, String newLicensePlate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LICENSE_PLATE, newLicensePlate);

        db.update(TABLE_VEHICLES, values, COLUMN_LICENSE_PLATE + " = ?", new String[]{oldLicensePlate});
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla cuando la base de datos se crea por primera vez
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si cambia la versión
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VEHICLES);
        onCreate(db);
    }
}
