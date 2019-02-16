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

    private ArrayList<Materia> materias;
    private RecyclerView recyclerViewMaterias;
    private TextView txtPpa;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materias=new ArrayList<>();
        txtPpa=findViewById(R.id.txtPpa);
        //Inicializacion del RecyclerView
        inicializarRecyclerView();
    }
    private void inicializarRecyclerView()
    {
        recyclerViewMaterias=findViewById(R.id.recyclerViewMaterias);
        recyclerViewMaterias.setLayoutManager(new LinearLayoutManager(this));
        final MateriaAdapter materiaAdapter= new MateriaAdapter();
        materiaAdapter.setMaterias(materias);
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
                materias.remove(viewHolder.getAdapterPosition());
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
                materias.add(materia);
                calculaPpa();
            }
        }
    }
    public void calculaPpa()
    {
        double sum=0.0;
        int sumCreditos=0;
        for(int i=0; i<materias.size();i++)
        {
            sum+=materias.get(i).getNota()*materias.get(i).getCreditos();
            sumCreditos+=materias.get(i).getCreditos();
        }
        txtPpa.setText("Tu ppa es de "+sum/sumCreditos);
    }
}
