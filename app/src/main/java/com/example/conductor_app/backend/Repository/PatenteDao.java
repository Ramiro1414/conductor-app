package com.example.conductor_app.backend.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conductor_app.backend.modelo.Patente;

import java.util.List;

@Dao
public interface PatenteDao {

    @Query("SELECT * FROM patente ORDER BY caracteres ASC")
    List<Patente> findAll();

    @Query("SELECT * FROM patente WHERE id = :id")
    Patente findById(int id);

    @Query("SELECT * FROM patente WHERE caracteres = :caracteres")
    Patente findByCaracteres(String caracteres);

    @Insert
    void insert(Patente patente);

    @Update
    void update(Patente patente);

    @Delete
    void delete(Patente patente);
}
