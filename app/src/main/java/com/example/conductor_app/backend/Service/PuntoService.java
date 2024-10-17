package com.example.conductor_app.backend.Service;

import android.content.Context;

import androidx.room.Room;

import com.example.conductor_app.backend.Repository.PuntoDao;
import com.example.conductor_app.backend.Repository.PuntoDataBase;
import com.example.conductor_app.backend.modelo.Punto;

import java.util.List;

public class PuntoService {

    private PuntoDao puntoDao;

    public PuntoService (Context context){
        PuntoDataBase puntoDataBase = Room.databaseBuilder(context, PuntoDataBase.class, "punto").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        puntoDao = puntoDataBase.puntoDao();
    }

    public List<Punto> findAll() {
        return this.puntoDao.findAll();
    }

    public Punto getById(int id) {
        return this.puntoDao.findById(id);
    }

    public void save(Punto punto) {
        this.puntoDao.insert(punto);
    }

    public void update(Punto punto){
        this.puntoDao.update(punto);
    }

    public void deleteAll() {
        this.puntoDao.deleteAll();
    }
}
