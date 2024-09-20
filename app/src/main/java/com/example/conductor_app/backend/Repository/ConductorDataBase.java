package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.conductor_app.backend.modelo.Conductor;

@Database(entities = {Conductor.class}, version = 1)
public abstract class ConductorDataBase extends RoomDatabase {

    public abstract ConductorDao conductorDao();

}
