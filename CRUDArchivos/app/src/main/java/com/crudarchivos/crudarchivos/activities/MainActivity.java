package com.crudarchivos.crudarchivos.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.crudarchivos.crudarchivos.R;
import com.crudarchivos.crudarchivos.adapters.MateriaAdapter;
import com.crudarchivos.crudarchivos.entities.Asignatura;
import com.crudarchivos.crudarchivos.services.ServicioAsignatura;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_AGREGAR_ASIGNATURA=1;
    private RecyclerView rv;
    private MateriaAdapter ma;
    private ServicioAsignatura servicioAsignatura;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.titAsignaturas));
        setSupportActionBar(toolbar);
        servicioAsignatura=ServicioAsignatura.getInstance(this.getFilesDir());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this, AgregarAsignatura.class);
                startActivityForResult(intent,REQUEST_CODE_AGREGAR_ASIGNATURA);

            }
        });

        FloatingActionButton buscar = findViewById(R.id.fabBuscar);
        buscar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this, BuscarAsignatura.class);
                startActivity(intent);

            }
        });

        inicializarRecyclerView();
    }

    private void inicializarRecyclerView()
    {
        rv=findViewById(R.id.recyclerTareas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ma= new MateriaAdapter();
        ma.setMaterias(servicioAsignatura.getAsignaturas());
        rv.setAdapter(ma);
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
                servicioAsignatura.eliminar(viewHolder.getAdapterPosition());
                ma.notifyDataSetChanged();
            }
        }).attachToRecyclerView(rv);
        //Evento al tocar un item del recycler view
        ma.setOnItemClickListener(new MateriaAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int posAsignatura)
            {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==REQUEST_CODE_AGREGAR_ASIGNATURA)
        {
            if(resultCode==RESULT_OK)
            {
                Asignatura asignatura=(Asignatura) data.getSerializableExtra(AgregarAsignatura.NUEVA_MATERIA);
                try {
                    servicioAsignatura.guardar(asignatura);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        ma.setMaterias(servicioAsignatura.getAsignaturas());
        ma.notifyDataSetChanged();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        ma.notifyDataSetChanged();
    }
}
