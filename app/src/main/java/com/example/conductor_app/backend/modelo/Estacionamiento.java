package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "estacionamiento")
public class Estacionamiento {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Long horaInicio;
    private Long horaFin;
    private int patenteId;

    public Estacionamiento() {
    }

    public Estacionamiento(int id, Long horaInicio, Long horaFin, int patenteId) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.patenteId = patenteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Long horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Long getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Long horaFin) {
        this.horaFin = horaFin;
    }

    public int getPatenteId() {
        return patenteId;
    }

    public void setPatenteId(int patenteId) {
        this.patenteId = patenteId;
    }

    @Override
    public String toString() {
        return "Estacionamiento{" +
                "id=" + id +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", patenteId=" + patenteId +
                '}';
    }
}
