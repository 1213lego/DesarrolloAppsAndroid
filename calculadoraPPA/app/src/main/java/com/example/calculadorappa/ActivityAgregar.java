package com.example.calculadorappa;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.calculadorappa.model.Materia;

import java.util.ArrayList;

public class ActivityAgregar extends AppCompatActivity
{
    public final static String NUEVA_MATERIA="NUEVA MATERIA";

    private EditText nombre;
    private EditText creditos;
    private EditText nota;

    private TextInputLayout inputNombre;
    private TextInputLayout inputNota;
    private TextInputLayout inputCreditos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        nombre=findViewById(R.id.txtNombre);
        nombre.addTextChangedListener(new TextWatcher()
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
                validarEditText(s,inputNombre);
            }
        });
        nombre.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(nombre.getText(),inputNombre);
                }
            }
        });

        creditos=findViewById(R.id.txtCreditos);
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
                validarEditText(s,inputCreditos);
            }
        });
        creditos.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(creditos.getText(),inputCreditos);
                }
            }
        });

        nota=findViewById(R.id.txtNota);
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
                validarEditText(s,inputNota);
            }
        });
        nota.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    validarEditText(nota.getText(),inputNota);
                }
            }
        });

        inputNombre=findViewById(R.id.inputNombre);
        inputNota=findViewById(R.id.inputNota);
        inputCreditos=findViewById(R.id.inputCreditos);
    }
    public void btnGuardar(View view)
    {
        validarEditText(nombre.getText(),inputNombre);
        validarEditText(creditos.getText(),inputCreditos);
        validarEditText(nota.getText(),inputNota);
        if(inputNombre.getError()!=null|| inputNota.getError()!=null || inputCreditos.getError()!=null)
        {

        }
        else
        {
            Materia materia= new Materia(nombre.getText().toString(),Integer.parseInt(creditos.getText().toString())
                    ,Double.parseDouble(nota.getText().toString()));
            Intent intent=new Intent();
            intent.putExtra(NUEVA_MATERIA,materia);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
    public void validarEditText(Editable editable, TextInputLayout textInputLayout)
    {
        if (TextUtils.isEmpty(editable))
        {
            textInputLayout.setError(getString(R.string.error_input));
        }
        else
        {
            textInputLayout.setError(null);
        }
    }


}
