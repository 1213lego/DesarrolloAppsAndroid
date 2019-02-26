package com.example.islas.calculadorappa.activities;

import android.app.DatePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.islas.calculadorappa.R;

import java.util.Calendar;
import java.util.Locale;

public class AgregarTareaActivity extends AppCompatActivity
{
    private int mYear,mMonth,mDay;
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
        nombre=findViewById(R.id.txtNomAsignatura);
        descripcion=findViewById(R.id.txtDescripcion);
        fecha=findViewById(R.id.txtFecha);
        porcentaje=findViewById(R.id.txtPorcentaje);
        hora=findViewById(R.id.txtHora);
        nota=findViewById(R.id.txtNota);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                fecha.setText(dayOfMonth+ "/" + monthOfYear+"/"+year);
            }
        };
    }

    public void btnHora(View view)
    {

    }
    public void btnFecha(View view)
    {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(AgregarTareaActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
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
    public void btnGuardar(View view)
    {

    }

}