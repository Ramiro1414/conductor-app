package com.example.conductor_app.backend.modelo;

public class Patente {

    private int id;
    private String caracteres;

    public Patente(int id, String caracteres) {
        this.id = id;
        this.caracteres = caracteres;
    }

    public Patente() {
    }

    public String getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Patente{" +
                "id=" + id +
                ", caracteres='" + caracteres + '\'' +
                '}';
    }
}
