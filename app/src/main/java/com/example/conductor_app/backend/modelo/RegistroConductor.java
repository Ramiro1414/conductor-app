package com.example.conductor_app.backend.modelo;

import java.sql.Timestamp;

public class RegistroConductor {

    private int id;

    private String patente;

    private Long horaInicio;

    private Long horaFin;

    public RegistroConductor(){}

    public RegistroConductor(String patente, Timestamp horaInicio, Timestamp horaFin) {
        id = 0;
        this.patente = patente;
        this.horaInicio = horaInicio.getTime();
        this.horaFin = horaFin.getTime();
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Timestamp getHoraInicio() {
        return new Timestamp(horaInicio);
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio.getTime();
    }

    public Timestamp getHoraFin() {
        return new Timestamp(horaFin);
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin.getTime();
    }

    @Override
    public String toString() {
        return "RegistroConductor{" +
                "patente='" + patente + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}
