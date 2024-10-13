package com.example.conductor_app.backend.modelo;

public class LineaPoligonoDTO {

    private int id;

    private PuntoDTO punto1;

    private PuntoDTO punto2;

    public LineaPoligonoDTO(int id, PuntoDTO punto1, PuntoDTO punto2) {
        this.id = id;
        this.punto1 = punto1;
        this.punto2 = punto2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PuntoDTO getPunto1() {
        return punto1;
    }

    public void setPunto1(PuntoDTO punto1) {
        this.punto1 = punto1;
    }

    public PuntoDTO getPunto2() {
        return punto2;
    }

    public void setPunto2(PuntoDTO punto2) {
        this.punto2 = punto2;
    }
}
