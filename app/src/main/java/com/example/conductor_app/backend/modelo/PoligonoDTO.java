package com.example.conductor_app.backend.modelo;

import java.util.Collection;

public class PoligonoDTO {

    private int id;

    private double precio;

    private String nombre;

    private Collection<LineaPoligonoDTO> lineasPoligono;

    public PoligonoDTO(int id, double precio, String nombre, Collection<LineaPoligonoDTO> lineasPoligono) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
        this.lineasPoligono = lineasPoligono;
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

    public Collection<LineaPoligonoDTO> getLineasPoligono() {
        return lineasPoligono;
    }

    public void setLineasPoligono(Collection<LineaPoligonoDTO> lineasPoligono) {
        this.lineasPoligono = lineasPoligono;
    }
}
