package com.example.islas.calculadorappa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Tarea;
import com.example.islas.calculadorappa.adapters.TareaAdapter;

import java.util.ArrayList;

public class tareasActivity extends AppCompatActivity
{
    private RecyclerView rv;
    private TareaAdapter ta;
    private ArrayList<Tarea> tareas;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        Intent intent=getIntent();
        tareas=(ArrayList<Tarea>)intent.getSerializableExtra(MainActivity.TAREAS);
        inicializarRecyclerView();
    }

    private void inicializarRecyclerView()
    {
        rv=findViewById(R.id.recyclerTareas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ta=new TareaAdapter();
        ta.setTareas(tareas);
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

}
