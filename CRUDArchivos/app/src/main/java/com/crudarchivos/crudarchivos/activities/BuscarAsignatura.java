package com.crudarchivos.crudarchivos.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crudarchivos.crudarchivos.R;
import com.crudarchivos.crudarchivos.entities.Asignatura;
import com.crudarchivos.crudarchivos.services.ServicioAsignatura;

public class BuscarAsignatura extends AppCompatActivity
{
    private EditText codigoABuscar;
    private CardView cardView;
    private TextView codigo;
    private TextView nombre;
    private TextView docente;
    private TextView creditos;
    private TextView semestre;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_asignatura);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_buscar));
        setSupportActionBar(toolbar);
        codigoABuscar=findViewById(R.id.codigoABuscar);
        cardView=findViewById(R.id.cardBuscar);
        cardView.setVisibility(View.INVISIBLE);
        codigo=findViewById(R.id.txtCodigoAsignatura);
        nombre=findViewById(R.id.txtNAsignatura);
        docente=findViewById(R.id.txtNDocente);
        creditos=findViewById(R.id.txtCreditosItem);
        semestre=findViewById(R.id.txtSemestre);
    }
    public void btnBuscar(View view)
    {
        if(!TextUtils.isEmpty(codigoABuscar.getText()))
        {
            cardView.setVisibility(View.INVISIBLE);
            Asignatura buscada= ServicioAsignatura.getInstance(null).buscarAsignatura(codigoABuscar.getText().toString());
            if(buscada!=null)
            {
                codigo.setText(buscada.getCodigoAsignatura().trim());
                docente.setText(buscada.getNombreDocente().trim());
                nombre.setText(buscada.getNombreAsignatura().trim());
                creditos.setText(buscada.getCreditos()+"");
                semestre.setText(buscada.getSemestre()+"");
                cardView.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(this, "No se ha encontrado la asignatura con codigo " + codigoABuscar.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
