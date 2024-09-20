package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.sql.Timestamp;

@Entity(tableName = "estacionamiento",
        foreignKeys = @ForeignKey(
        entity = Patente.class,
        parentColumns = "id",
        childColumns = "patenteId",
        onDelete = ForeignKey.CASCADE))
public class Estacionamiento {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Timestamp horaInicio;
    private Timestamp horaFin;
    private int patenteId; // Clave foránea que referencia a la tabla Patente

    public Estacionamiento() {
    }

    public Estacionamiento(int id, Timestamp horaInicio, Timestamp horaFin, int patenteId) {
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
