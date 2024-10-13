package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "linea_poligono")
public class LineaPoligono {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int poligonoId;
    private int puntoInicioId;
    private int puntoFinId;

    public LineaPoligono() {
    }

    public LineaPoligono(int id, int poligonoId, int puntoInicioId, int puntoFinId) {
        this.id = id;
        this.poligonoId = poligonoId;
        this.puntoInicioId = puntoInicioId;
        this.puntoFinId = puntoFinId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoligonoId() {
        return poligonoId;
    }

    public void setPoligonoId(int poligonoId) {
        this.poligonoId = poligonoId;
    }

    public int getPuntoInicioId() {
        return puntoInicioId;
    }

    public void setPuntoInicioId(int puntoInicioId) {
        this.puntoInicioId = puntoInicioId;
    }

    public int getPuntoFinId() {
        return puntoFinId;
    }

    public void setPuntoFinId(int puntoFinId) {
        this.puntoFinId = puntoFinId;
    }

    @Override
    public String toString() {
        return "LineaPoligono{" +
                "id=" + id +
                ", poligonoId=" + poligonoId +
                ", puntoInicioId=" + puntoInicioId +
                ", puntoFinId=" + puntoFinId +
                '}';
    }
}
