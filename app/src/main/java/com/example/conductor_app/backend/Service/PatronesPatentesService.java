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
        PatronPatenteDataBase db = Room.databaseBuilder(context, PatronPatenteDataBase.class, "patron_patente").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        this.patronPatenteDao = db.patronPatenteDao();
    }

    public PatronesPatentesService(PatronPatenteDao patronPatenteDao) {
        this.patronPatenteDao = patronPatenteDao;
    }

    public List<PatronPatente> findAll() {
        return this.patronPatenteDao.findAll();
    }

    public PatronPatente findById(int id) {
        return this.patronPatenteDao.findById(id);
    }

    public void save(PatronPatente patronPatente) {
        this.patronPatenteDao.insert(patronPatente);
    }

    public void update(PatronPatente patronPatente) throws PatenteRepetidaException {
        this.patronPatenteDao.update(patronPatente);
    }

    public void delete(int id) {
        PatronPatente patronPatente = this.patronPatenteDao.findById(id);
        if (patronPatente != null) {
            this.patronPatenteDao.delete(patronPatente);
        }
    }

    public void deleteAll(){
        this.patronPatenteDao.deleteAll();
    }

}
