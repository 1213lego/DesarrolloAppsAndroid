package com.example.islas.calculadorappa.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.islas.calculadorappa.adapters.MateriaAdapter;
import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.servicios.ServicioCalPPA;

public class MainActivity extends AppCompatActivity
{
    public final static int REQUEST_CODE_AGREGAR = 0;
    public final static String ASIGNATURA ="Asignatura";
    public final static String POS_ASIGNATURA ="Posicion";

    private TextView txtPPA;
    private ServicioCalPPA servicioCalPPA;

    private RecyclerView rv;
    private MateriaAdapter ma;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        txtPPA= findViewById(R.id.txtPPA);
        servicioCalPPA=ServicioCalPPA.getInstance(this.getFilesDir());
        inicializarRecyclerView();
        servicioCalPPA.listar();
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
    }
    public void onclickPlusUno(View view)
    {
        servicioCalPPA.aumentarCreditosEnUno();
        ma.notifyDataSetChanged();
    }
    private void inicializarRecyclerView()
    {
        rv=findViewById(R.id.recyclerTareas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ma= new MateriaAdapter();
        ma.setMaterias(servicioCalPPA.getAsignaturas());
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
                servicioCalPPA.eliminar(viewHolder.getAdapterPosition());
                ma.notifyDataSetChanged();
                calculaPpa();
            }
        }).attachToRecyclerView(rv);
        //Evento al tocar un item del recycler view
        ma.setOnItemClickListener(new MateriaAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int posAsignatura)
            {
                Intent intent=new Intent(MainActivity.this, TareasActivity.class);
                intent.putExtra(POS_ASIGNATURA,posAsignatura);
                startActivity(intent);
            }
        });
    }

    public void agregarAsignatura(View view)
    {
        Intent intent = new Intent(this, AgregarAsignatura.class);
        startActivityForResult(intent,REQUEST_CODE_AGREGAR);
    }
    private void calculaPpa()
    {
        txtPPA.setText(String.format("%.2f",servicioCalPPA.calcularPPA()));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CODE_AGREGAR)
        {
            if(resultCode==RESULT_OK)
            {
                Asignatura asignatura=(Asignatura) data.getSerializableExtra(AgregarAsignatura.NUEVA_MATERIA);
                try {
                    servicioCalPPA.guardar(asignatura);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        ma.notifyDataSetChanged();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        ma.notifyDataSetChanged();
        calculaPpa();
    }

}

