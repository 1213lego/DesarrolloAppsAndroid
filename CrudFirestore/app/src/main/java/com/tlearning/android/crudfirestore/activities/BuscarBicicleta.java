package com.tlearning.android.crudfirestore.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.tlearning.android.crudfirestore.R;
import com.tlearning.android.crudfirestore.callbacks.FirestoreCallback;
import com.tlearning.android.crudfirestore.model.Bicicleta;
import com.tlearning.android.crudfirestore.service.ServicioBicicleta;

public class BuscarBicicleta extends AppCompatActivity {
    private AutoCompleteTextView textViewSearch;
    private TextView serial;
    private TextView marca;
    private TextView rin;
    private TextView velocidades;
    private TextView peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_bicicleta);
        serial= findViewById(R.id.textViewSerial);
        marca= findViewById(R.id.textViewMarca);
        rin= findViewById(R.id.textViewRin);
        velocidades= findViewById(R.id.textViewVelocidades);
        peso= findViewById(R.id.textViewPeso);
        textViewSearch=findViewById(R.id.textViewSearch);
    }

    public void onClickFabButtonSearch(View view) {
        if(!TextUtils.isEmpty(textViewSearch.getText())){
            Bicicleta aBuscar=new Bicicleta();
            aBuscar.setSerial(textViewSearch.getText().toString());
            showProgressDialog();
            ServicioBicicleta.getInstance().buscarBicicleta(aBuscar, new FirestoreCallback<Bicicleta>() {
                @Override
                public void onSuccess(Bicicleta bicicleta) {
                    if(bicicleta!=null){
                        bindDataBicicleta(bicicleta);
                    }
                    else{
                        cleanFields();
                        Toast.makeText(BuscarBicicleta.this, "La bicicleta buscada no existe", Toast.LENGTH_SHORT).show();
                    }
                    hideProgressDialog();
                }
                @Override
                public void onFailure(Exception exception) {
                    hideProgressDialog();
                    Toast.makeText(BuscarBicicleta.this, "Problemas al ejecutar la busqueda " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void bindDataBicicleta(Bicicleta bicicleta){
        serial.setText(bicicleta.getSerial());
        marca.setText(bicicleta.getMarca());
        velocidades.setText(String.valueOf(bicicleta.getVelocidades()));
        rin.setText(String.valueOf(bicicleta.getRin()));
        peso.setText(String.valueOf(bicicleta.getPeso()));
    }
    private void cleanFields(){
        serial.setText(null);
        marca.setText(null);
        velocidades.setText(null);
        rin.setText(null);
        peso.setText(null);
    }
    public ProgressDialog mProgressDialog;
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.cargando));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
