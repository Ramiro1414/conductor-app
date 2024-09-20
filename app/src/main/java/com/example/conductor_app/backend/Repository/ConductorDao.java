package com.example.conductor_app.backend.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conductor_app.backend.modelo.Conductor;

import java.util.List;

@Dao
public interface ConductorDao {
    @Query("SELECT * FROM conductor ORDER BY id")
    List<Conductor> findAll();

    @Query("SELECT * FROM conductor WHERE id = :id")
    Conductor findById(int id);

    @Insert
    void insert(Conductor conductor);

    @Update
    void update(Conductor conductor);

    @Delete
    void delete(Conductor conductor);
}
