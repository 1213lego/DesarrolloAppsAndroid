package com.example.islas.calculadorappa.servicios;

import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.entities.Tarea;

import java.util.ArrayList;


public class ServicioCalPPA
{
    public final static ServicioCalPPA SERVICIOPPA = new ServicioCalPPA();
    private ArrayList<Asignatura> asignaturas;
    private double ppa;

    private ServicioCalPPA()
    {
        this.asignaturas = new ArrayList<Asignatura>();
        this.ppa = 0.0;
    }

    public double calcularPPA()
    {
        double creditosAcumulados= 0;
        double notaProductoAcumulado= 0.0;
        for (int i=0; i< asignaturas.size() ; i++)
        {
            creditosAcumulados = creditosAcumulados+asignaturas.get(i).getCreditos();
            notaProductoAcumulado= notaProductoAcumulado+asignaturas.get(i).productoNotaxCredito();


        }
        ppa = notaProductoAcumulado/creditosAcumulados;
        return ppa;

    }

    public int progresoAsignatura(int pos)
    {
        ArrayList<Tarea> tareas = asignaturas.get(pos).getTareas();
        double suma = 0;
        for(int i=0 ; i<tareas.size();i++)
        {
           suma= tareas.get(i).getNota() * tareas.get(i).getPorcentaje();

        }
        int resultado = (int) (suma * 100) / 5;
    return resultado;
    }

    public double darNotaAsignatura(int pos)

    {
        ArrayList<Tarea> tareas = asignaturas.get(pos).getTareas();
        double suma = 0;
        for(int i=0 ; i<tareas.size();i++)
        {
            suma= tareas.get(i).getNota() * tareas.get(i).getPorcentaje();

        }
        return suma;
    }

    public void añadirAsignatura(Asignatura asignatura)
    {
        asignaturas.add(asignatura);
    }

    public ArrayList<Asignatura> getAsignaturas()
    {
        return asignaturas;
    }


    public void eliminarMateria(int adapterPosition)
    {
        asignaturas.remove(adapterPosition);
    }
}
