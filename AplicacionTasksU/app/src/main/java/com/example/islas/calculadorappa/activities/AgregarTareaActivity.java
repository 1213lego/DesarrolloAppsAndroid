package com.example.islas.calculadorappa.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.islas.calculadorappa.R;
import com.example.islas.calculadorappa.entities.Tarea;
import java.util.Calendar;
import java.util.Date;
public class AgregarTareaActivity extends AppCompatActivity
{

    public final static String NUEVA_TAREA= "NUEVA TAREA";
    public final static String ACCION="Accion";
    public final static String EDITAR="Editar";
    public final static String GUARDAR="Guardar";
    private int mYear,mMonth,mDay,mHora,mMinutos;

    private String accion;
    private int posTareaEdicion;
    private EditText nombre;
    private EditText descripcion;
    private EditText fecha;
    private EditText hora;
    private EditText porcentaje;
    private EditText nota;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarea);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombre=findViewById(R.id.txtNombreTarea);
        descripcion=findViewById(R.id.txtDescripcion);
        fecha=findViewById(R.id.txtFecha);
        porcentaje=findViewById(R.id.txtPorcentaje);
        hora=findViewById(R.id.txtHora);
        nota=findViewById(R.id.txtNota);
        Intent intent=getIntent();
        Tarea tarea=(Tarea)intent.getSerializableExtra(TareasActivity.TAREA);
        accion=intent.getStringExtra(ACCION);
        if(accion.equals(EDITAR))
        {
            nombre.setText(tarea.getNombre());
            descripcion.setText(tarea.getDescripcion());
            porcentaje.setText(tarea.getPorcentaje()+"");
            Date date=tarea.getFecha();
            Calendar.getInstance().setTime(date);
            nota.setVisibility(View.VISIBLE);
            nota.setText(tarea.getNota()+"");
            posTareaEdicion=intent.getIntExtra(TareasActivity.POS_TAREA,-1);
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            hora.setText(date.getHours() + " : " + date.getMinutes());
            fecha.setText(formateador.format(date));
        }



    }
    private void showTimePicker()
    {
        TimePickerDialog tpd = new TimePickerDialog(AgregarTareaActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                hora.setText(hourOfDay+" : "+minute);
            }
        },mHora,mMinutos,false);
        tpd.show();
    }

    public void btnHora(View view)
    {
        final Calendar c = Calendar.getInstance();
        mHora= c.get(Calendar.HOUR);
        mMinutos= c.get(Calendar.MINUTE);
        showTimePicker();
    }
    private void showDatePicker()
    {
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(AgregarTareaActivity.this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {
                        // Display Selected date in textbox
                        if (year < mYear)
                            view.updateDate(mYear,mMonth,mDay);

                        if (monthOfYear < mMonth && year == mYear)
                            view.updateDate(mYear,mMonth,mDay);

                        if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                            view.updateDate(mYear,mMonth,mDay);

                        fecha.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }
    public void btnFecha(View view)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        showDatePicker();
    }
    public void btnGuardar(View view)
    {

        if(accion.equals(EDITAR))
        {
            Date fechD = new Date(mYear,mMonth,mDay,mHora,mMinutos,0);
            Tarea tarea = new Tarea(nombre.getText().toString(), descripcion.getText().toString(), fechD, Double.parseDouble(porcentaje.getText().toString()));
            tarea.setNota(Double.parseDouble(nota.getText().toString()));
            Intent intent = new Intent();
            intent.putExtra(EDITAR, tarea);
            intent.putExtra(TareasActivity.POS_TAREA,posTareaEdicion);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if(accion.equals(GUARDAR))
        {
            Date fechD = new Date(mYear,mMonth,mDay,mHora,mMinutos,0);
            Tarea tarea = new Tarea(nombre.getText().toString(), descripcion.getText().toString(), fechD, Double.parseDouble(porcentaje.getText().toString()));
            Intent intent = new Intent();
            intent.putExtra(NUEVA_TAREA, tarea);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

}
