package com.crudarchivos.crudarchivos.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Asignatura implements Serializable
{

    private String nombreAsignatura;
    private String nombreDocente;
    private int creditos;
    private int semestre;

    public Asignatura(String nombreAsignatura, String nombreDocente, int numerocreditos, int semestre)
    {
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
        this.creditos = numerocreditos;
        this.semestre=semestre;
    }


    public String getNombreAsignatura()
    {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura)
    {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getNombreDocente()
    {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
}
