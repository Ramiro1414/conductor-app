package com.example.conductor_app.backend.Repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.conductor_app.backend.modelo.HoraInicioHoraFin;


@Database(entities = {HoraInicioHoraFin.class}, version = 1)
public abstract class HoraInicioHoraFinDataBase extends RoomDatabase {

    public abstract HoraInicioHoraFinDao horaInicioHoraFinDao();

}
