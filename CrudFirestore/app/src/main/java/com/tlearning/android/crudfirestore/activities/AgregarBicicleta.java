package com.tlearning.android.crudfirestore.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tlearning.android.crudfirestore.R;
import com.tlearning.android.crudfirestore.model.Bicicleta;

import java.io.Serializable;

public class AgregarBicicleta extends AppCompatActivity {

    public static final String BICICLETA = "Bici";
    private Spinner spinnerRines;
    private TextInputEditText serial;
    private TextInputEditText marca;
    private TextInputEditText velocidades;
    private TextInputEditText peso;
    private ArrayAdapter<CharSequence> adapterSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bicicleta);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        spinnerRines=findViewById(R.id.numberPicker);
        setUpSpinnerData();
        serial=findViewById(R.id.serial);
        marca=findViewById(R.id.marca);
        velocidades=findViewById(R.id.velocidades);
        peso=findViewById(R.id.peso);
        Intent intent= getIntent();
        Serializable serializable=intent.getSerializableExtra(BICICLETA);
        if(serializable!=null){
            Bicicleta bicicleta= (Bicicleta) serializable;
            bindDataForUpdate(bicicleta);
        }
    }
    public void bindDataForUpdate(Bicicleta bicicleta){
        serial.setText(bicicleta.getSerial());
        serial.setEnabled(false);
        marca.setText(bicicleta.getMarca());
        velocidades.setText(String.valueOf(bicicleta.getVelocidades()));
        peso.setText(String.valueOf(bicicleta.getPeso()));
        spinnerRines.setSelection(adapterSpinner.getPosition(String.valueOf(bicicleta.getRin())));
    }
    private void setUpSpinnerData() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapterSpinner= ArrayAdapter.createFromResource(this,
                R.array.rines_bicicleta_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRines.setAdapter(adapterSpinner);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agregar_bicicleta, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                guardar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void guardar() {
        if(TextUtils.isEmpty(serial.getText()) || TextUtils.isEmpty(marca.getText()) ||
            TextUtils.isEmpty(velocidades.getText()) || TextUtils.isEmpty(peso.getText()) ||
            spinnerRines.getSelectedItem()==null){
            return;
        }
        Bicicleta bicicleta=new Bicicleta(serial.getText().toString(), marca.getText().toString(), Integer.parseInt(velocidades.getText().toString()),
                Double.parseDouble((String) spinnerRines.getSelectedItem()), Double.parseDouble(peso.getText().toString()));
        Intent data=new Intent();
        data.putExtra(BICICLETA,bicicleta);
        setResult(RESULT_OK,data);
        finish();
    }
}
