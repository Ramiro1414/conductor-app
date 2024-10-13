package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.conductor_app.backend.Repository.Converter;

import java.sql.Timestamp;
import java.util.Date;

@Entity(tableName = "horario_estacionamiento")

public class HorarioEstacionamiento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(Converter.class)
    private Date fechaInicio;
    @TypeConverters(Converter.class)
    private Date fechaFin;

    private Timestamp horaInicio;
    private Timestamp horaFin;
    private String nombre;

    public HorarioEstacionamiento() {
    }

    public HorarioEstacionamiento(Date fechaInicio, Date fechaFin, Timestamp horaInicio, Timestamp horaFin, String nombre) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "HorarioEstacionamiento{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

