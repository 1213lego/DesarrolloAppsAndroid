package com.example.islas.calculadorappa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.entities.Tarea;
import com.example.islas.calculadorappa.adapters.TareaAdapter;
import com.example.islas.calculadorappa.servicios.ServicioCalPPA;

public class TareasActivity extends AppCompatActivity
{
    public final static int REQUEST_CODE_AGREGEGAR_TAREA = 1;
    public final static int REQUEST_CODE_EDITAR_TAREA = 5;
    public final static String TAREA="Tarea";
    public final static String POS_TAREA="Posicion tarea";
    public final static String PORCENTAJE_ACTUAL="Porcentaje Actual";

    private RecyclerView rv;
    private TareaAdapter ta;
    private Asignatura asignatura;
    private ProgressBar progressBar;
    private int posAsignatura;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        Intent intent=getIntent();
        posAsignatura=intent.getIntExtra(MainActivity.POS_ASIGNATURA,-1);
        asignatura=ServicioCalPPA.getInstance().getAsignaturas().get(posAsignatura);

        FloatingActionButton fab = findViewById(R.id.fab);
        progressBar=findViewById(R.id.progressBarTareas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.titleToolbarTareas)+" "+asignatura.getNombreAsignatura());
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(TareasActivity.this,AgregarTareaActivity.class);
                intent.putExtra(PORCENTAJE_ACTUAL,asignatura.getPorcentajeActual());
                startActivityForResult(intent,REQUEST_CODE_AGREGEGAR_TAREA);
            }
        });
        setProgressBar();
        inicializarRecyclerView();
    }

    private void inicializarRecyclerView()
    {
        rv=findViewById(R.id.recyclerTareas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ta=new TareaAdapter();
        ta.setTareas(asignatura.getTareas());
        rv.setAdapter(ta);
        //Evento al tocar un item del recycler view
        ta.setOnClickListener(new TareaAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int posTarea)
            {
                Intent intent= new Intent(TareasActivity.this,EditarActivity.class);
                intent.putExtra(POS_TAREA,posTarea);
                intent.putExtra(TAREA,asignatura.getTareas().get(posTarea));
                intent.putExtra(PORCENTAJE_ACTUAL,asignatura.getPorcentajeActual());
                startActivityForResult(intent,REQUEST_CODE_EDITAR_TAREA);
            }
        });
    }

    public void setProgressBar()
    {
        progressBar.setProgress(ServicioCalPPA.getInstance().progresoAsignatura(posAsignatura));
        ServicioCalPPA.getInstance().setNotaAsignatura(posAsignatura,ServicioCalPPA.getInstance().darNotaAsignatura(posAsignatura));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_AGREGEGAR_TAREA)
        {
            if(resultCode==RESULT_OK)
            {
                Tarea tarea = (Tarea) data.getSerializableExtra(AgregarTareaActivity.NUEVA_TAREA);
                asignatura.agregarTarea(tarea);
                ta.notifyDataSetChanged();
            }
        }
        else if(requestCode==REQUEST_CODE_EDITAR_TAREA)
        {
            if(resultCode==RESULT_OK)
            {
                Tarea tarea = (Tarea) data.getSerializableExtra(EditarActivity.TAREA_EDITADA);
                int posTarea=data.getIntExtra(POS_TAREA,-1);
                asignatura.getTareas().set(posTarea,tarea);
                ta.notifyDataSetChanged();
            }
        }
        setProgressBar();
    }
}
