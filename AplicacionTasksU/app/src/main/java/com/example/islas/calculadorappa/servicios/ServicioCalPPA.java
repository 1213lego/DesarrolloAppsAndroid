package com.example.islas.calculadorappa.servicios;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.entities.Tarea;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class ServicioCalPPA
{
    private static final String NOMBRE_ARCHIVO="asignaturas.txt";
    private ArrayList<Asignatura> asignaturas;
    private double ppa;
    private static ServicioCalPPA instance;
    private File archivoAsignaturas;
    private RandomAccessFile raf;
    private ServicioCalPPA(File file)
    {
        this.asignaturas = new ArrayList<Asignatura>();
        archivoAsignaturas=new File(file.getPath(),NOMBRE_ARCHIVO);
        this.ppa = 0.0;
    }

    public static ServicioCalPPA getInstance(File file)
    {
        if(instance==null)
        {
            instance=new ServicioCalPPA(file);
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

    public void guardar(Asignatura asignatura) throws Exception
    {
        try
        {
            raf=new RandomAccessFile(archivoAsignaturas,"rw");
            String existe=existe(asignatura.getCodigoAsignatura());
            if(existe==null)
            {
                raf.seek(archivoAsignaturas.length());
                raf.writeUTF(asignatura.darCampos());
                raf.close();
                asignaturas.add(asignatura);
            }
            else
            {
                throw new Exception("No se ha podido guarda porque ya exite una asigantura con el codigo "+ asignatura.getCodigoAsignatura().trim());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public int buscarAsignatura(String codigoBuscado)
    {
        int posicion=-1;
        for (int i = 0; i < asignaturas.size(); i++)
        {
            if(asignaturas.get(i).getCodigoAsignatura().trim().equals(codigoBuscado.trim()))
            {
                posicion=i;
                break;
            }
        }
        return posicion;
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
    public void listar()
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
    public void actualizar(Asignatura aActualizar, int posAEditar)
    {
        try
        {
            raf=new RandomAccessFile(archivoAsignaturas,"rw");
            raf.seek(0);
            while (raf.getFilePointer()<archivoAsignaturas.length())
            {
                String actual=raf.readUTF();
                int tam=actual.length();
                String [] partes=actual.split(String.valueOf(Asignatura.SEPARADOR));
                if(aActualizar.getCodigoAsignatura().trim().equals(partes[0].trim()))
                {
                    raf.seek(raf.getFilePointer()-(2+tam));
                    raf.writeUTF(aActualizar.darCampos());
                    asignaturas.set(posAEditar,aActualizar);
                    break;
                }
            }
            raf.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void aumentarCreditosEnUno()
    {
        for (int i = 0; i < asignaturas.size(); i++) {
            asignaturas.get(i).setCreditos(asignaturas.get(i).getCreditos()+1);
            actualizar(asignaturas.get(i),i);
        }
    }
}
