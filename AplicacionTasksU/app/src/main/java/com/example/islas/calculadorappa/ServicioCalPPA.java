package com.example.islas.calculadorappa;

import android.widget.TextView;

import java.util.ArrayList;


public class ServicioCalPPA
{
    private ArrayList<Asignatura> asignaturas;
    private double ppa;

    public ServicioCalPPA()
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
