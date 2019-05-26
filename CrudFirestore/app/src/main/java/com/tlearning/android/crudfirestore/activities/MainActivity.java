package com.tlearning.android.crudfirestore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tlearning.android.crudfirestore.R;
import com.tlearning.android.crudfirestore.adapter.BicicletasAdapter;
import com.tlearning.android.crudfirestore.callbacks.OnClickItemListener;
import com.tlearning.android.crudfirestore.model.Bicicleta;
import com.tlearning.android.crudfirestore.callbacks.FirestoreCallback;
import com.tlearning.android.crudfirestore.service.ServicioBicicleta;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_AGREGAR_BICICLETA = 2;
    private static final int REQUEST_CODE_ACTUALIZAR_BICICLETA=3;
    private ServicioBicicleta servicioBicicleta;
    private RecyclerView recyclerViewBicis;
    private BicicletasAdapter currentBicicletasAdapter;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        servicioBicicleta= ServicioBicicleta.getInstance();
        setUpRecyclerView();
        setUpRadioGroup();

    }

    private void setUpRadioGroup() {
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.orderPeso){
                    changeQuery(FirebaseFirestore.getInstance().collection(Bicicleta.COLLECTION_NAME).orderBy("peso", Query.Direction.ASCENDING));
                }
                else if(checkedId == R.id.orderMarca){
                    changeQuery(FirebaseFirestore.getInstance().collection(Bicicleta.COLLECTION_NAME).orderBy("marca", Query.Direction.DESCENDING));
                }
                else if(checkedId == R.id.orderDefault){
                    changeQuery(FirebaseFirestore.getInstance().collection(Bicicleta.COLLECTION_NAME));
                }
            }
        });
    }
    private void changeQuery(Query query){
        stopListeningData();
        FirestoreRecyclerOptions<Bicicleta> options = new FirestoreRecyclerOptions.Builder<Bicicleta>()
                .setQuery(query,Bicicleta.class)
                .build();
        currentBicicletasAdapter =new BicicletasAdapter(options);
        recyclerViewBicis.setAdapter(currentBicicletasAdapter);
        currentBicicletasAdapter.setOnClickItemListener(onClickItemListener);
        startListeningData();
    }
    public void onClickFabButtonAdd(View view){
        Intent intent=new Intent(MainActivity.this,AgregarBicicleta.class);
        startActivityForResult(intent,REQUEST_CODE_AGREGAR_BICICLETA);
    }
    public void  onClickFabButtonSearch(View view){
        Intent intent=new Intent(this,BuscarBicicleta.class);
        startActivity(intent);
    }
    private void setUpRecyclerView() {
        recyclerViewBicis= findViewById(R.id.rvBicicletas);
        Query query= servicioBicicleta.darQueryBicicletas();
        FirestoreRecyclerOptions<Bicicleta> options = new FirestoreRecyclerOptions.Builder<Bicicleta>()
                .setQuery(query,Bicicleta.class)
                .build();
        currentBicicletasAdapter =new BicicletasAdapter(options);
        recyclerViewBicis.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBicis.setAdapter(currentBicicletasAdapter);
        //funcionalidad que permite eliminar un elemento deslizandolo
        //da la funcionalidad de swipe and move a cada item del recycler view
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                showProgressDialog();
                servicioBicicleta.eliminarBicicleta(currentBicicletasAdapter.getItem(viewHolder.getAdapterPosition()),
                        new FirestoreCallback<Bicicleta>() {
                            @Override
                            public void onSuccess(Bicicleta bicicleta) {
                                Toast.makeText(MainActivity.this, "Se ha eliminado la bicicleta", Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                            }
                        });
            }
        }).attachToRecyclerView(recyclerViewBicis);
        currentBicicletasAdapter.setOnClickItemListener(onClickItemListener);
    }
    private OnClickItemListener<Bicicleta> onClickItemListener=new OnClickItemListener<Bicicleta>() {
        @Override
        public void onClickItem(Bicicleta bicicleta) {
            Intent intent=new Intent(MainActivity.this,AgregarBicicleta.class);
            intent.putExtra(AgregarBicicleta.BICICLETA,bicicleta);
            startActivityForResult(intent,REQUEST_CODE_ACTUALIZAR_BICICLETA);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteAll) {
            servicioBicicleta.deleteAll(new FirestoreCallback<Bicicleta>() {
                @Override
                public void onSuccess(Bicicleta bicicleta) {
                    Toast.makeText(MainActivity.this, "Se ha eliminado toda la coleccion", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(MainActivity.this, "Problemas al eliminar la coleccion "+ exception.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void guardarBicicleta(Bicicleta bicicleta){
        showProgressDialog();
        servicioBicicleta.guardarBicicleta(bicicleta, new FirestoreCallback<Bicicleta>() {
            @Override
            public void onSuccess(Bicicleta bicicleta) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "Se ha guardado la bicicleta", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Exception exception) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_AGREGAR_BICICLETA){
            if(resultCode==RESULT_OK){
                Bicicleta bicicleta= (Bicicleta) data.getSerializableExtra(AgregarBicicleta.BICICLETA);
                guardarBicicleta(bicicleta);
            }
        }
        else if(requestCode==REQUEST_CODE_ACTUALIZAR_BICICLETA){
            if(resultCode==RESULT_OK){
                Bicicleta bicicleta= (Bicicleta) data.getSerializableExtra(AgregarBicicleta.BICICLETA);
                actualizarBicicleta(bicicleta);
            }
        }
    }

    private void actualizarBicicleta(Bicicleta bicicleta) {
        showProgressDialog();
        servicioBicicleta.actualizarBicicle(bicicleta, new FirestoreCallback<Bicicleta>() {
            @Override
            public void onSuccess(Bicicleta bicicleta) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "Se ha actualizado la bicicleta", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
    @Override
    protected void onStart() {
        super.onStart();
        startListeningData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopListeningData();
    }
    private void stopListeningData(){
        currentBicicletasAdapter.stopListening();
    }
    private void startListeningData(){
        currentBicicletasAdapter.startListening();
    }

}
