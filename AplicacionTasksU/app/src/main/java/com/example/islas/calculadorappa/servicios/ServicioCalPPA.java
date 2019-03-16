package com.example.islas.calculadorappa.servicios;

import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.entities.Tarea;

import java.util.ArrayList;


public class ServicioCalPPA
{

    private ArrayList<Asignatura> asignaturas;
    private double ppa;
    private static ServicioCalPPA instance;
    private ServicioCalPPA()
    {
        this.asignaturas = new ArrayList<Asignatura>();
        this.ppa = 0.0;
    }

    public static ServicioCalPPA getInstance()
    {
        if(instance ==null)
        {
            instance =new ServicioCalPPA();
        }
        return instance;
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
        if(creditosAcumulados==0.0)
        {
            return 0;
        }
        ppa = notaProductoAcumulado/creditosAcumulados;
        return ppa;

    }

    public void setNotaAsignatura(int pos,double notafinal)
    {
        asignaturas.get(pos).setNotafinal(notafinal);
    }

    public int progresoAsignatura(int pos)
    {
        ArrayList<Tarea> tareas = asignaturas.get(pos).getTareas();
        double suma = 0.0;
        for(int i=0 ; i<tareas.size();i++)
        {
           suma+= (int) tareas.get(i).getNota()*(tareas.get(i).getPorcentaje()/100);

        }
        int resultado = (int) (suma*100)/5;
    return resultado;
    }
    public double darNotaAsignatura(int pos)

    {
        ArrayList<Tarea> tareas = asignaturas.get(pos).getTareas();
        double suma = 0.0;
        for(int i=0 ; i<tareas.size();i++)
        {
            suma+= tareas.get(i).getNota() * (tareas.get(i).getPorcentaje()/100);

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
