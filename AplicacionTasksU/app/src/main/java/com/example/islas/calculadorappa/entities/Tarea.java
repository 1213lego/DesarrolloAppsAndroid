package com.example.islas.calculadorappa.entities;

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable
{

    private String nombre;
    private String descripcion;
    private Date fecha;
    private double porcentaje;
    private double nota;

    public Tarea(String nombre, String descripcion, Date fecha, double porcentaje)
    {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.porcentaje = porcentaje;
        this.nota = 0.0;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getNota()
    {
        return nota;
    }

    public void setNota(double nota){
        this.nota=nota;
    }
    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
