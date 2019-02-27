package com.example.islas.calculadorappa.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Asignatura implements Serializable
{

    private String nombreAsignatura;
    private String nombreDocente;
    private int creditos;
    private double notafinal;
    private double notaProducto;
    private ArrayList<Tarea> tareas;
    private double progreso;

    public Asignatura(String nombreAsignatura, String nombreDocente, int numerocreditos, double notafinal)
    {
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
        this.creditos = numerocreditos;
        this.notafinal = notafinal;
        this.notaProducto=0.0;
        this.tareas=new ArrayList<>();
        this.progreso = 0.0;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int numerocreditos) {
        this.creditos = numerocreditos;
    }

    public double getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(double notafinal) {
        this.notafinal = notafinal;
    }

    public double productoNotaxCredito()
    {
        notaProducto=notafinal*creditos;
        return notaProducto;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void agregarTarea(Tarea tarea)
    {
        tareas.add(tarea);
    }
}
