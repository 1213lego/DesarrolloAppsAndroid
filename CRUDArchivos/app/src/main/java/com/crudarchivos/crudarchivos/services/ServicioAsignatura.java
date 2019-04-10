package com.crudarchivos.crudarchivos.services;

import com.crudarchivos.crudarchivos.entities.Asignatura;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ServicioAsignatura
{
    private static final String NOMBRE_ARCHIVO="asignaturas.txt";
    private ArrayList<Asignatura> asignaturas;
    private File archivoAsignaturas;
    private RandomAccessFile raf;
    public ServicioAsignatura(File file)
    {
        asignaturas=new ArrayList<>();
        archivoAsignaturas=new File(file.getPath(),NOMBRE_ARCHIVO);
    }
    public ArrayList<Asignatura> getAsignaturas()
    {
        listar();
        return asignaturas;
    }

    public void guardar(Asignatura asignatura)
    {
        try
        {
            raf=new RandomAccessFile(archivoAsignaturas,"rw");
            if(existe(asignatura.getCodigoAsignatura())==null)
            {
                raf.seek(archivoAsignaturas.length());
                raf.writeUTF(asignatura.darCampos());
                raf.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private String existe(String codigoBuscado)
    {
        String resultado=null;
        try
        {
            raf.seek(0);
            while (raf.getFilePointer()<archivoAsignaturas.length())
            {
                String actual=raf.readUTF();
                String [] partes=actual.split(String.valueOf(Asignatura.SEPARADOR));
                char [] codigoActual=partes[0].trim().toCharArray();
                char [] buscado=codigoBuscado.trim().toCharArray();
                boolean igual= codigoActual.length == buscado.length ? true : false;
                if(igual)
                {
                    for (int i = 0; i < codigoActual.length; i++)
                    {
                        if(codigoActual[i]!=buscado[i])
                        {
                            igual=false;
                            break;
                        }
                    }
                }
                if(igual)
                {
                    resultado=actual;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return resultado;
    }
    private void listar()
    {
        asignaturas.clear();
        try
        {
            raf=new RandomAccessFile(archivoAsignaturas,"r");
            raf.seek(0);
            while (raf.getFilePointer()<archivoAsignaturas.length())
            {
                String actual=raf.readUTF();
                String [] partes=actual.split(String.valueOf(Asignatura.SEPARADOR));
                Asignatura asignatura=new Asignatura(partes[0],partes[1],partes[2],Integer.parseInt(partes[3].trim()),Integer.parseInt(partes[4].trim()));
                if(partes[5].equals("1"))
                {
                    asignaturas.add(asignatura);
                }
            }
            raf.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void eliminar(int posAsignatura)
    {
        Asignatura aEliminar=asignaturas.get(posAsignatura);
        try
        {
            raf=new RandomAccessFile(archivoAsignaturas,"rw");
            raf.seek(0);
            while (raf.getFilePointer()<archivoAsignaturas.length())
            {
                String actual=raf.readUTF();
                int tam=actual.length();
                String [] partes=actual.split(String.valueOf(Asignatura.SEPARADOR));
                aEliminar.setEstado(Asignatura.ELIMINADO);
                if(aEliminar.getCodigoAsignatura().trim().equals(partes[0].trim()))
                {
                    raf.seek(raf.getFilePointer()-(2+tam));
                    raf.writeUTF(aEliminar.darCampos());
                    break;
                }
            }
            raf.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        asignaturas.remove(posAsignatura);
    }
}
