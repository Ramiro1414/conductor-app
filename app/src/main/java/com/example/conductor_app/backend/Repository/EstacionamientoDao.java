package com.example.conductor_app.backend.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conductor_app.backend.modelo.Estacionamiento;

import java.util.List;

@Dao
public interface EstacionamientoDao {
    @Query("SELECT * FROM estacionamiento ORDER BY id")
    List<Estacionamiento> findAll();

    @Query("SELECT * FROM estacionamiento WHERE id = :id")
    Estacionamiento findById(int id);

    @Insert
    void insert(Estacionamiento estacionamiento);

    @Update
    void update(Estacionamiento estacionamiento);

    @Delete
    void delete(Estacionamiento estacionamiento);
}

