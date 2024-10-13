package com.example.conductor_app.backend.Service;

import android.content.Context;

import androidx.room.Room;

import com.example.conductor_app.backend.Repository.HorarioEstacionamientoDao;
import com.example.conductor_app.backend.Repository.HorarioEstacionamientoDataBase;
import com.example.conductor_app.backend.modelo.HorarioEstacionamiento;

import java.util.List;

public class HorarioEstacionamientoService {

    private HorarioEstacionamientoDao horarioEstacionamientoDao;

    public HorarioEstacionamientoService (Context context){
        HorarioEstacionamientoDataBase horarioEstacionamientoDataBase = Room.databaseBuilder(context, HorarioEstacionamientoDataBase.class, "horario_estacionamiento").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        horarioEstacionamientoDao = horarioEstacionamientoDataBase.horarioEstacionamientoDao();
    }

    public List<HorarioEstacionamiento> findAll() {
        return this.horarioEstacionamientoDao.findAll();
    }

    public HorarioEstacionamiento getById(int id) {
        return this.horarioEstacionamientoDao.findById(id);
    }

    public void save(HorarioEstacionamiento horarioEstacionamiento){
        this.horarioEstacionamientoDao.insert(horarioEstacionamiento);
    }

    public void update(HorarioEstacionamiento horarioEstacionamiento){
        this.horarioEstacionamientoDao.update(horarioEstacionamiento);
    }

    public void deleteAll() {
        this.horarioEstacionamientoDao.deleteAll();
    }
}
