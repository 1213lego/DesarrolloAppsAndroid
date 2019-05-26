package com.tlearning.android.crudfirestore.model;

import java.io.Serializable;

public class Bicicleta implements Serializable {
    public static final String COLLECTION_NAME = "bicicletas";
    private String serial;
    private String marca;
    private int velocidades;
    private double rin;
    private double peso;
    public Bicicleta(){

    }

    public Bicicleta(String serial, String marca, int velocidades, double rin, double peso) {
        this.serial = serial;
        this.marca = marca;
        this.velocidades = velocidades;
        this.rin = rin;
        this.peso = peso;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getVelocidades() {
        return velocidades;
    }

    public void setVelocidades(int velocidades) {
        this.velocidades = velocidades;
    }

    public double getRin() {
        return rin;
    }

    public void setRin(double rin) {
        this.rin = rin;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
                "serial='" + serial + '\'' +
                ", marca='" + marca + '\'' +
                ", velocidades=" + velocidades +
                ", rin=" + rin +
                ", peso=" + peso +
                '}';
    }
}
