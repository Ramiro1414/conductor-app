package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.conductor_app.backend.modelo.PatronPatente;

@Database(entities = {PatronPatente.class}, version = 2)
public abstract class PatronPatenteDataBase extends RoomDatabase {

    public abstract PatronPatenteDao patronPatenteDao();

}