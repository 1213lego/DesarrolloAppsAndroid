package com.crudarchivos.crudarchivos.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    private EditText nombre;
    private EditText docente;
    private EditText creditos;
    private EditText semestre;
    private Button btnEditar;
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
        nombre.setEnabled(false);
        docente=findViewById(R.id.txtNDocente);
        docente.setEnabled(false);
        creditos=findViewById(R.id.txtCreditosItem);
        creditos.setEnabled(false);
        semestre=findViewById(R.id.txtSemestre);
        semestre.setEnabled(false);
        btnEditar=findViewById(R.id.btnActualizar);
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
    public void btnEditar(View view)
    {
        try
        {
            Asignatura asignatura=new Asignatura(codigo.getText().toString(),
                    nombre.getText().toString().trim(), docente.getText().toString().trim(),Integer.parseInt(creditos.getText().toString().trim())
            , Integer.parseInt(semestre.getText().toString().trim()));
            ServicioAsignatura.getInstance(null).actualizar(asignatura);
            finish();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void btnHabilitarEdicion(View view)
    {
        nombre.setEnabled(true);
        docente.setEnabled(true);
        semestre.setEnabled(true);
        creditos.setEnabled(true);
        btnEditar.setVisibility(View.VISIBLE);
    }

}
