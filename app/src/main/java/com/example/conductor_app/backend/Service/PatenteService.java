package com.example.conductor_app.backend.Service;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.conductor_app.backend.Repository.PatenteDao;
import com.example.conductor_app.backend.Repository.PatenteDataBase;
import com.example.conductor_app.backend.modelo.Patente;
import com.example.conductor_app.backend.modelo.PatronPatente;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;

public class PatenteService {

    private PatenteDao patenteDao;
    private Context context;

    public PatenteService(Context context) {
        this.context = context;
        PatenteDataBase db = Room.databaseBuilder(context, PatenteDataBase.class, "patentes").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        patenteDao = db.patenteDao();
    }

    public PatenteService(PatenteDao patenteDao) {
        this.patenteDao = patenteDao;
    }

    public List<Patente> getAllPatentes() {
        return patenteDao.findAll();
    }

    public Patente getPatenteById(int id) {
        return patenteDao.findById(id);
    }

    public Patente getPatenteByCaracteres(String caracteres) {
        caracteres = formatear(caracteres);
        return patenteDao.findByCaracteres(caracteres);
    }

    public void savePatente(Patente patente) throws PatenteRepetidaException {
        patente.setCaracteres(formatear(patente.getCaracteres()));
        Patente existente = patenteDao.findByCaracteres(patente.getCaracteres());
        if (existente != null) {
            throw new PatenteRepetidaException("La patente ya existe");
        } else {
            patenteDao.insert(patente);
        }
    }

    public void updatePatente(Patente patente) throws PatenteRepetidaException {
        patente.setCaracteres(formatear(patente.getCaracteres()));
        Patente existente = patenteDao.findByCaracteres(patente.getCaracteres());
        if (existente != null && existente.getId() != patente.getId()) {
            throw new PatenteRepetidaException("La patente ya existe");
        } else {
            patenteDao.update(patente);
        }
    }

    public void deletePatente(int id) {
        Patente patente = patenteDao.findById(id);
        if (patente != null) {
            patenteDao.delete(patente);
        }
    }

    private boolean formatoValido(String caracteres) {
        List<String> regexList = new ArrayList<>();

        PatronesPatentesService patronesPatentesService = new PatronesPatentesService(this.context);
        for(PatronPatente p : patronesPatentesService.findAll()) {
            regexList.add(p.getExpresionRegularPatente());
            Log.d("AAAAA", p.toString());
        }

        Log.d("AAAAAAAAAAAAAAAAAAAAAA", patronesPatentesService.findAll().size() + "");

        for(String regex : regexList){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(caracteres);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    private String formatear(String caracteres) {
        caracteres = caracteres.replace(" ", "").toUpperCase();

        // Comprobar si el formato es válido
        if (!(formatoValido(caracteres))) {
            throw new IllegalArgumentException("La patente no cumple con el formato requerido");
        }

        return caracteres;
    }
}
