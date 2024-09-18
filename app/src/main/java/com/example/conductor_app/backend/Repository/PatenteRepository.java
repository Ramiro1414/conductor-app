package com.example.conductor_app.backend.Repository;

import com.example.conductor_app.backend.Service.PatenteRepetidaException;
import com.example.conductor_app.backend.modelo.Patente;

import java.util.List;

public interface PatenteRepository {

    List<Patente> findAll();

    Patente findById(int id);

    Patente findByCaracteres(String caracteres);

    void save(Patente patente) throws PatenteRepetidaException;

    void deleteById(int  id);

    void updateById(Patente patente) throws PatenteRepetidaException;


}