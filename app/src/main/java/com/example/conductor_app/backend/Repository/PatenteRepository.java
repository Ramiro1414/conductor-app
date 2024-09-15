package com.example.conductor_app.backend.Repository;

import com.example.conductor_app.backend.modelo.Patente;

import java.util.List;

public interface PatenteRepository {

    List<Patente> findAll();

    Patente findById(int id);

    Patente findByCaracteres(String caracteres);

    void save(Patente patente);

    void deleteById(int  id);

    void updateById(int id, Patente patente);


}