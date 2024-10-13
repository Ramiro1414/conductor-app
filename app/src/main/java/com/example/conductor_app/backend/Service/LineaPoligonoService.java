package com.example.conductor_app.backend.Service;

import android.content.Context;

import androidx.room.Room;

import com.example.conductor_app.backend.Repository.LineaPoligonoDao;
import com.example.conductor_app.backend.Repository.LineaPoligonoDataBase;
import com.example.conductor_app.backend.modelo.LineaPoligono;

import java.util.List;

public class LineaPoligonoService {

    private LineaPoligonoDao lineaPoligonoDao;

    public LineaPoligonoService (Context context){
        LineaPoligonoDataBase horarioEstacionamientoDataBase = Room.databaseBuilder(context, LineaPoligonoDataBase.class, "linea_poligono").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        lineaPoligonoDao = horarioEstacionamientoDataBase.lineaPoligonoDao();
    }

    public List<LineaPoligono> findAll() {
        return this.lineaPoligonoDao.findAll();
    }

    public LineaPoligono getById(int id) {
        return this.lineaPoligonoDao.findById(id);
    }

    public void save(LineaPoligono lineaPoligono){
        this.lineaPoligonoDao.insert(lineaPoligono);
    }

    public void update(LineaPoligono lineaPoligono){
        this.lineaPoligonoDao.update(lineaPoligono);
    }

    public void deleteAll() {
        this.lineaPoligonoDao.deleteAll();
    }
}
