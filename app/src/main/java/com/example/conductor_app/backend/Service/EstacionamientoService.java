package com.example.conductor_app.backend.Service;

import android.content.Context;

import androidx.room.Room;

import com.example.conductor_app.backend.Repository.EstacionamientoDao;
import com.example.conductor_app.backend.Repository.EstacionamientoDataBase;
import com.example.conductor_app.backend.modelo.Estacionamiento;

import java.util.ArrayList;
import java.util.List;

public class EstacionamientoService {

    private EstacionamientoDao estacionamientoDao;

    public EstacionamientoService (Context context){
        EstacionamientoDataBase estacionamientoDataBase = Room.databaseBuilder(context, EstacionamientoDataBase.class, "estacionamiento").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        estacionamientoDao = estacionamientoDataBase.estacionamientoDao();
    }

    public EstacionamientoService (EstacionamientoDao estacionamientoDao){
        this.estacionamientoDao = estacionamientoDao;
    }

    public List<Estacionamiento> findAll() {
        return this.estacionamientoDao.findAll();
    }
    public Estacionamiento getById(int id) {
        return this.estacionamientoDao.findById(id);
    }

    public void save(Estacionamiento estacionamiento){
        this.estacionamientoDao.insert(estacionamiento);
    }

    public List<Estacionamiento> findUncompleted (){
        List<Estacionamiento> result = new ArrayList<>();
        for(Estacionamiento e : this.estacionamientoDao.findAll())
            if(e.getHoraFin() == null)
                result.add(e);

        return result;
    }

    public void update(Estacionamiento estacionamiento){
        this.estacionamientoDao.update(estacionamiento);
    }

}
