package com.lego.firestore.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;
import java.io.Serializable;
public class Nota implements Serializable
{
    public static final String NAME_COLLECTION ="notas";
    private String titulo;
    private String contenido;
    @ServerTimestamp
    private Timestamp fecha;
    private String userId;
    public Nota()
    {
    }
    public Nota(String titulo,String contenido)
    {
        this.contenido=contenido;
        this.titulo=titulo;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
