package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patente")
public class Patente {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String caracteres;

    // Constructor
    public Patente(int id, String caracteres) {
        this.id = id;
        this.caracteres = caracteres;
    }

    public Patente() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    @Override
    public String toString() {
        return "Patente{" +
                "id=" + id +
                ", caracteres='" + caracteres + '\'' +
                '}';
    }
}
