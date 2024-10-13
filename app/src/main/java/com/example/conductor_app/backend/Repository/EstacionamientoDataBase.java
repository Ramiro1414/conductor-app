package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.conductor_app.backend.modelo.Estacionamiento;
import com.example.conductor_app.backend.modelo.Patente;

@Database(entities = {Estacionamiento.class, Patente.class}, version = 14)
@TypeConverters({Converter.class})
public abstract class EstacionamientoDataBase extends RoomDatabase {

    public abstract EstacionamientoDao estacionamientoDao();
    public abstract PatenteDao patenteDao();

}
