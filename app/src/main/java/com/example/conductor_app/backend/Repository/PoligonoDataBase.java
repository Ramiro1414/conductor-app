package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.conductor_app.backend.modelo.Poligono;

@Database(entities = {Poligono.class}, version = 2)
public abstract class PoligonoDataBase extends RoomDatabase {

    public abstract PoligonoDao poligonoDao();

}