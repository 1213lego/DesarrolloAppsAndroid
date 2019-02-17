package com.example.calculadorappa;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculadorappa.model.Materia;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static final int REQUEST_CODE_AGREGAR_MATERIA=0;

    private ServicioPpa servicioPpa;
    //Para el recyclerview
    private RecyclerView recyclerViewMaterias;
    private MateriaAdapter materiaAdapter;

    private TextView txtPpa;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        servicioPpa=new ServicioPpa();
        txtPpa=findViewById(R.id.txtPpa);
        //Inicializacion del RecyclerView
        inicializarRecyclerView();
    }
    private void inicializarRecyclerView()
    {
        recyclerViewMaterias=findViewById(R.id.recyclerViewMaterias);
        recyclerViewMaterias.setLayoutManager(new LinearLayoutManager(this));
        materiaAdapter= new MateriaAdapter();
        materiaAdapter.setMaterias(servicioPpa.getMaterias());
        recyclerViewMaterias.setAdapter(materiaAdapter);
        //funcionalidad que permite eliminar un elemento deslizandolo
        //da la funcionalidad de swipe and move a cada item del recycler view
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                Toast.makeText(MainActivity.this, "onMove()", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                servicioPpa.eliminarMateria(viewHolder.getAdapterPosition());
                recyclerViewMaterias.removeViewAt(viewHolder.getAdapterPosition());
                materiaAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                materiaAdapter.notifyDataSetChanged();
                calculaPpa();
            }
        }).attachToRecyclerView(recyclerViewMaterias);
    }
    public void btnMateria(View view)
    {
        Intent intent= new Intent(this,ActivityAgregar.class);
        startActivityForResult(intent,REQUEST_CODE_AGREGAR_MATERIA);
    }

    //Cuando se termina de ejecutar la actividad previamente lanzada, el sistema ejecuta el siguiente metodo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==REQUEST_CODE_AGREGAR_MATERIA)
        {
            if(resultCode==RESULT_OK)
            {
                Materia materia=(Materia) data.getSerializableExtra(ActivityAgregar.NUEVA_MATERIA);
                servicioPpa.agregarMateria(materia);
                materiaAdapter.notifyDataSetChanged();
                calculaPpa();
            }
        }
    }
    public void calculaPpa()
    {

        txtPpa.setText("Tu ppa es de: "+String.format("%.2f",servicioPpa.calcularPpa()));
    }
}
