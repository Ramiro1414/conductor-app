package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.conductor_app.backend.modelo.Patente;

@Database(entities = {Patente.class}, version = 1)
public abstract class PatenteDataBase extends RoomDatabase {

    public abstract PatenteDao patenteDao();

}
