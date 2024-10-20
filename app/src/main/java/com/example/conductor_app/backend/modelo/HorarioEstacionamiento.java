package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "horario_estacionamiento")
public class HorarioEstacionamiento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;

    private String fechaInicio;
    private String fechaFin;

    public HorarioEstacionamiento() {
    }

    public HorarioEstacionamiento(int id, String nombre, String fechaInicio, String fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}

