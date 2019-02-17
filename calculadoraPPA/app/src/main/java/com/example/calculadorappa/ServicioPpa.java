package com.example.calculadorappa;

import com.example.calculadorappa.model.Materia;

import java.util.ArrayList;

public class ServicioPpa
{
    private ArrayList<Materia> materias;

    public ServicioPpa()
    {
        materias=new ArrayList<>();
    }

    public ArrayList<Materia> getMaterias()
    {
        return materias;
    }

    public void agregarMateria(Materia materia)
    {
        materias.add(materia);
    }

    public void eliminarMateria(int  pos)
    {
        materias.remove(pos);
    }
    public double calcularPpa()
    {
        double sum=0.0;
        int sumCreditos=0;
        for(int i=0; i<materias.size();i++)
        {
            sum+=materias.get(i).getNota()*materias.get(i).getCreditos();
            sumCreditos+=materias.get(i).getCreditos();
        }
        return sum/sumCreditos;
    }

}
