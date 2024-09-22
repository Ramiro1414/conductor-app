package com.example.conductor_app.backend.Service;

import android.content.Context;
import androidx.room.Room;

import com.example.conductor_app.backend.Repository.PatenteDao;
import com.example.conductor_app.backend.Repository.PatenteDataBase;
import com.example.conductor_app.backend.modelo.Patente;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;

public class PatenteService {

    private PatenteDao patenteDao;

    public PatenteService(Context context) {
        PatenteDataBase db = Room.databaseBuilder(context, PatenteDataBase.class, "patentes").allowMainThreadQueries().build();
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

    private boolean formatoInvalido(String caracteres) {
        // Definir las expresiones regulares
        String regex1 = "^[A-Z]{3}\\d{3}$";             // 3 letras seguidas de 3 números
        String regex2 = "^[A-Z]{2}\\d{3}[A-Z]{2}$";     // 2 letras seguidas de 3 números y 2 letras

        // Compilar las expresiones regulares
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);

        // Crear un matcher para ambas expresiones
        Matcher matcher1 = pattern1.matcher(caracteres);
        Matcher matcher2 = pattern2.matcher(caracteres);

        return (!matcher1.matches() && !matcher2.matches());
    }

    private String formatear(String caracteres) {
        caracteres = caracteres.replace(" ", "").toUpperCase();

        // Comprobar si el formato es válido
        if (formatoInvalido(caracteres)) {
            throw new IllegalArgumentException("La patente no cumple con el formato requerido");
        }

        return caracteres;
    }
}
