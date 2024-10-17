package com.example.conductor_app.backend.Service;

import android.content.Context;

import androidx.room.Room;

import com.example.conductor_app.backend.Repository.HoraInicioHoraFinDao;
import com.example.conductor_app.backend.Repository.HoraInicioHoraFinDataBase;
import com.example.conductor_app.backend.modelo.HoraInicioHoraFin;

import java.util.List;

public class HoraInicioHoraFinService {

    private HoraInicioHoraFinDao horaInicioHoraFinDao;

    public HoraInicioHoraFinService (Context context){
        HoraInicioHoraFinDataBase horaInicioHoraFinDataBase = Room.databaseBuilder(context, HoraInicioHoraFinDataBase.class, "hora_inicio_hora_fin").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        horaInicioHoraFinDao = horaInicioHoraFinDataBase.horaInicioHoraFinDao();
    }

    public List<HoraInicioHoraFin> findAll() {
        return this.horaInicioHoraFinDao.findAll();
    }

    public HoraInicioHoraFin getById(int id) {
        return this.horaInicioHoraFinDao.findById(id);
    }

    public void save(HoraInicioHoraFin horaInicioHoraFin){
        this.horaInicioHoraFinDao.insert(horaInicioHoraFin);
    }

    public void update(HoraInicioHoraFin horaInicioHoraFin){
        this.horaInicioHoraFinDao.update(horaInicioHoraFin);
    }

    public void deleteAll() {
        this.horaInicioHoraFinDao.deleteAll();
    }

}
