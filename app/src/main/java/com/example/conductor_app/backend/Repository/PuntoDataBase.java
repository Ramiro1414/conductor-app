package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.conductor_app.backend.modelo.Punto;

@Database(entities = {Punto.class}, version = 1)
public abstract class PuntoDataBase extends RoomDatabase {

    public abstract PuntoDao puntoDao();

}