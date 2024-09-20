package com.example.conductor_app.backend.modelo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "conductor")
public class Conductor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double saldo;

    public Conductor() {
    }

    public Conductor(int id, double saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + id +
                ", saldo=" + saldo +
                '}';
    }
}
