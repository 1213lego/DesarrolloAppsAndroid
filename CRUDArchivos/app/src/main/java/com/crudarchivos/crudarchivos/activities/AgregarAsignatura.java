package com.crudarchivos.crudarchivos.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crudarchivos.crudarchivos.R;
import com.crudarchivos.crudarchivos.entities.Asignatura;

public class AgregarAsignatura extends AppCompatActivity
{
    public final static String NUEVA_MATERIA= "NUEVA MATERIA";
    private EditText codigoAsignatura;
    private EditText nombreAsignatura;
    private EditText nombreDocente;
    private EditText creditos;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_asignatura);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.agregarAsignatura));
        setSupportActionBar(toolbar);
        spinner=findViewById(R.id.spinnerSemestre);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semestres_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        inicializarEditText();
    }
    private void inicializarEditText()
    {
        nombreAsignatura=findViewById(R.id.txtNomAsignatura);
        nombreDocente=findViewById(R.id.txtNomDocente);
        creditos=findViewById(R.id.txtNumCreditos);
        codigoAsignatura=findViewById(R.id.txtCodigoAsignatura);
        codigoAsignatura.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {
                validarEditText(codigoAsignatura);
            }
        });
        codigoAsignatura.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(codigoAsignatura);
                }
            }
        });
        nombreAsignatura.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                validarEditText(nombreAsignatura);
            }
        });
        nombreAsignatura.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(nombreAsignatura);
                }
            }
        });

        nombreDocente.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Toast.makeText(AgregarAsignatura.this, ""+count, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                validarEditText(nombreDocente);
            }
        });
        nombreDocente.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(nombreDocente);
                }
            }
        });

        creditos.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                validarEditText(creditos);
            }
        });
        creditos.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(creditos);
                }
            }
        });
    }
    //Valida si un campo de texto es vacio
    public void validarEditText(TextView textView)
    {
        if (TextUtils.isEmpty(textView.getText()))
        {
            textView.setError(getString(R.string.error_input));
        }
        else
        {
            textView.setError(null);
        }

    }

    public void agregarAsignatura(View view)
    {
        validarEditText(nombreAsignatura);
        validarEditText(nombreDocente);
        validarEditText(creditos);

        if(nombreAsignatura.getError()!=null|| nombreDocente.getError()!=null
                || creditos.getError()!=null )
        {
            return;
        }
        else
        {
            Asignatura asignatura= null;
            try
            {
                asignatura = new Asignatura(codigoAsignatura.getText().toString(),nombreAsignatura.getText().toString(),
                        nombreDocente.getText().toString(),Integer.parseInt(creditos.getText().toString()),spinner.getSelectedItemPosition()+1);
                Intent intent = new Intent();
                intent.putExtra(NUEVA_MATERIA,asignatura);
                setResult(RESULT_OK,intent);
                finish();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void backButton(View view)
    {
        finish();
    }
}
