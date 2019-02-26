package com.example.islas.calculadorappa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.islas.calculadorappa.entities.Asignatura;
import com.example.islas.calculadorappa.R;

public class AgregarAsignatura extends AppCompatActivity
{
    public final static String NUEVA_MATERIA= "NUEVA MATERIA";
    private EditText nombreAsignatura;
    private EditText nombreDocente;
    private EditText nota;
    private EditText creditos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_asignatura);
        inicializarEditText();
    }
    private void inicializarEditText()
    {
        nombreAsignatura=findViewById(R.id.txtNomAsignatura);
        nombreDocente=findViewById(R.id.txtNomDocente);
        nota=findViewById(R.id.txtNotFInal);
        creditos=findViewById(R.id.txtNumCreditos);

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

        nota.addTextChangedListener(new TextWatcher()
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
                validarEditText(nota);
            }
        });
        nota.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(nota);
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
        validarEditText(nota);
        if(nombreAsignatura.getError()!=null|| nombreDocente.getError()!=null
                || creditos.getError()!=null || nota.getError()!=null)
        {
            return;
        }
        else
        {
            Asignatura asignatura= new Asignatura(nombreAsignatura.getText().toString(),
                    nombreDocente.getText().toString(),Integer.parseInt(creditos.getText().toString()),
                    Double.parseDouble(nota.getText().toString()));
            Intent intent = new Intent();
            intent.putExtra(NUEVA_MATERIA,asignatura);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    public void backButton(View view)
    {
        finish();
    }
}
