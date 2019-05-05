package com.lego.firestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lego.firestore.model.Nota;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class AgregarNota extends AppCompatActivity
{
    public static final String NUEVA_NOTA="Nueva nota";
    private EditText titulo;
    private EditText contenio;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        setTitle("Agregar nota");
        titulo=findViewById(R.id.titulo);
        contenio=findViewById(R.id.contenido);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agregar_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregarNota:
                guardar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void guardar()
    {
        Nota nota=new Nota(titulo.getText().toString(),contenio.getText().toString());
        Intent data=new Intent();
        data.putExtra(NUEVA_NOTA,nota);
        setResult(RESULT_OK,data);
        finish();
    }
}
