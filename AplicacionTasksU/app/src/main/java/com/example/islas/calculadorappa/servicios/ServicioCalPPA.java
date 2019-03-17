package com.example.islas.calculadorappa.servicios;

import com.example.islas.calculadorappa.R;
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
           suma+= tareas.get(i).getNota()*(tareas.get(i).getPorcentaje()/100);

        }
        int resultado = (int) (suma*100)/5;
    return resultado;
    }
    public int darEmojiAsigantura(int pos)
    {
        double nota=asignaturas.get(pos).getNotafinal();
        int result=0;
        if(nota<=0.6)
        {
            result= R.drawable.dizzyface;
        }
        else if(nota>0.6 && nota<=1.2)
        {
            result=R.drawable.cryingface;
        }
        else if(nota>1.2 && nota <=1.8)
        {
            result=R.drawable.loudly;
        }
        else if(nota>1.8 && nota<=2.4)
        {
            result=R.drawable.worried;
        }
        else if(nota>2.4 && nota<=3.0)
        {
            result=R.drawable.thinking;
        }
        else if(nota>3.0 && nota<=3.6)
        {
            result=R.drawable.relieved;
        }
        else if(nota>3.6 && nota<=4.2)
        {
            result=R.drawable.grimacing;
        }
        else if(nota>4.2 && nota<=4.8)
        {
            result=R.drawable.grinning;
        }
        else
        {
            result=R.drawable.nerd;
        }
        return result;
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
