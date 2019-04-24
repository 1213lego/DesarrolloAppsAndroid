package com.example.islas.calculadorappa.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Asignatura implements Serializable
{
    public static final int TAMANIO_CODIGO=6;
    public static final int TAMANIO_NOMBRE=60;
    public static final int TAMANIO_DOCENTE =100;
    public static final int TAMANIO_CREDITOS=2;
    public static final int TAMANIO_SEMESTRE =2;
    public static final int TAMANIO_REGISTRO=TAMANIO_CODIGO+TAMANIO_NOMBRE+TAMANIO_DOCENTE+TAMANIO_CREDITOS+TAMANIO_SEMESTRE+6;
    public static final char SEPARADOR =',';
    public static final char ELIMINADO='0';
    public static final char ACTIVO='1';
    private String codigoAsignatura;
    private String nombreAsignatura;
    private String nombreDocente;
    private int creditos;
    private int semestre;
    private char estado;
    private ArrayList<Tarea> tareas;

    private double notafinal;
    private double notaProducto;
    private double porcentajeActual;

    public Asignatura(String codigoAsignatura, String nombreAsignatura, String nombreDocente, int numerocreditos, int semestre) throws Exception
    {
        this.codigoAsignatura=codigoAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
        this.creditos = numerocreditos;
        this.semestre=semestre;
        this.codigoAsignatura=verficarCadena(this.codigoAsignatura,TAMANIO_CODIGO);
        this.nombreAsignatura=verficarCadena(this.nombreAsignatura,TAMANIO_NOMBRE);
        this.nombreDocente=verficarCadena(this.nombreDocente, TAMANIO_DOCENTE);
        verficarCadena(String.valueOf(creditos),TAMANIO_CREDITOS);
        verficarCadena(String.valueOf(semestre)+"",TAMANIO_SEMESTRE);
        estado=ACTIVO;
        this.tareas=new ArrayList<>();
    }

    private String verficarCadena(String cadena, int tamMax) throws Exception
    {
        int tamCadena=cadena.length();
        if(tamCadena>tamMax)
        {
            throw new Exception("El campo donde escribio "+ cadena+" no puede tener mas de " +tamMax+ " caracteres");
        }
        else
        {
            while (cadena.length()<tamMax)
            {
                cadena+=" ";
            }
        }
        return cadena;
    }
    private String convertirEntero(String cadena, int tamMax)
    {
        int tamCadena=cadena.length();
        while (cadena.length()<tamMax)
        {
            cadena+=" ";
        }
        return cadena;
    }
    public String darCampos()
    {
        return codigoAsignatura+ SEPARADOR+ nombreAsignatura + SEPARADOR + nombreDocente+ SEPARADOR +
                convertirEntero(String.valueOf(creditos),TAMANIO_CREDITOS)+SEPARADOR+convertirEntero(String.valueOf(semestre),TAMANIO_SEMESTRE)
                +SEPARADOR + estado;
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

    public String getCodigoAsignatura() {
        return codigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        this.codigoAsignatura = codigoAsignatura;
    }

    public void setEstado(char estado)
    {
        this.estado = estado;
    }


    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
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
