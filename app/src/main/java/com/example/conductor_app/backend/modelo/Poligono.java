package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "poligono")
public class Poligono {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double precio;
    private String nombre;

    public Poligono() {
    }

    public Poligono(int id, double precio, String nombre) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Poligono{" +
                "id=" + id +
                ", precio=" + precio +
                '}';
    }
}
