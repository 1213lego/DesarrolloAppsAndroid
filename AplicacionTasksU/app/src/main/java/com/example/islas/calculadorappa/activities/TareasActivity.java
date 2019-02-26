package com.example.islas.calculadorappa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.entities.Tarea;
import com.example.islas.calculadorappa.adapters.TareaAdapter;

import java.util.ArrayList;

public class TareasActivity extends AppCompatActivity
{
    public final static int REQUEST_CODE_AGREGEGAR_TAREA = 1;
    public final static String TAREA ="TAREA";

    private RecyclerView rv;
    private TareaAdapter ta;
    private Asignatura asignatura;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        FloatingActionButton fab = findViewById(R.id.fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent=getIntent();
        asignatura=(Asignatura) intent.getSerializableExtra(MainActivity.ASIGNATURA);
        toolbar.setTitle(getString(R.string.titleToolbarTareas)+" "+asignatura.getNombreAsignatura());
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(TareasActivity.this,AgregarTareaActivity.class);

                startActivityForResult(intent,REQUEST_CODE_AGREGEGAR_TAREA);
            }
        });

        inicializarRecyclerView();
    }

    private void inicializarRecyclerView()
    {
        rv=findViewById(R.id.recyclerTareas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ta=new TareaAdapter();
        ta.setTareas(asignatura.getTareas());
        rv.setAdapter(ta);
        //funcionalidad que permite eliminar un elemento deslizandolo
        //da la funcionalidad de swipe and move a cada item del recycler view
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {

            }
        }).attachToRecyclerView(rv);
        //Evento al tocar un item del recycler view
        ta.setOnClickListener(new TareaAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(Tarea tarea)
            {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_AGREGEGAR_TAREA)
        {
            if(resultCode==RESULT_OK)
            {
                //se obtiene la nueva tarea
            }
        }
    }
}
