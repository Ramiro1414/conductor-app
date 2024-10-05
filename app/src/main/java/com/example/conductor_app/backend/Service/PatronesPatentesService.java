package com.example.conductor_app.backend.Service;

import android.content.Context;
import androidx.room.Room;

import com.example.conductor_app.backend.Repository.PatronPatenteDao;
import com.example.conductor_app.backend.Repository.PatronPatenteDataBase;
import com.example.conductor_app.backend.modelo.PatronPatente;

import java.util.List;

public class PatronesPatentesService {

    private PatronPatenteDao patronPatenteDao;

    public PatronesPatentesService(Context context) {
        PatronPatenteDataBase db = Room.databaseBuilder(context, PatronPatenteDataBase.class, "patentes").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        patronPatenteDao = db.patronPatenteDao();
    }

    public PatronesPatentesService(PatronPatenteDao patronPatenteDao) {
        this.patronPatenteDao = patronPatenteDao;
    }

    public List<PatronPatente> findAll() {
        return patronPatenteDao.findAll();
    }

    public PatronPatente findById(int id) {
        return patronPatenteDao.findById(id);
    }

    public void save(PatronPatente patronPatente) {
        patronPatenteDao.insert(patronPatente);
    }

    public void update(PatronPatente patronPatente) throws PatenteRepetidaException {
        patronPatenteDao.update(patronPatente);
    }

    public void delete(int id) {
        PatronPatente patronPatente = patronPatenteDao.findById(id);
        if (patronPatente != null) {
            patronPatenteDao.delete(patronPatente);
        }
    }

    public void deleteAll(){
        patronPatenteDao.deleteAll();
    }

}
