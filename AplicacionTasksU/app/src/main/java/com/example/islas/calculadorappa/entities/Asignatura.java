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
    private double porcentajeActual;
    private int semestre;

    public Asignatura(String nombreAsignatura, String nombreDocente, int numerocreditos, int semestre)
    {
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
        this.creditos = numerocreditos;
        this.notafinal = 0.0;
        this.notaProducto=0.0;
        this.tareas=new ArrayList<>();
        this.semestre=semestre;
        porcentajeActual=0.0;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public int getCreditos() {
        return creditos;
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
        porcentajeActual+=tarea.getPorcentaje();
    }

    public double getPorcentajeActual() {
        return porcentajeActual;
    }

    public double getNotafinal() {
        return notafinal;
    }
}
