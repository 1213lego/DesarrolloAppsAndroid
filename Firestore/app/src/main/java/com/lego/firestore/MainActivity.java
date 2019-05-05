package com.lego.firestore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lego.firestore.model.Nota;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_CODE_AGREGAR_NOTA = 5;
    private FirebaseFirestore dbFirsFirestore;
    private RecyclerView recyclerView;
    private NotasAdapter notasAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbFirsFirestore=FirebaseFirestore.getInstance();
        inicializarRecyclerView();
    }

    private void inicializarRecyclerView()
    {
        recyclerView=findViewById(R.id.recyclerViewNotas);
        Query query=dbFirsFirestore.collection(Nota.NAME_COLLECTION).whereEqualTo("userId",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("fecha",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Nota> options= new FirestoreRecyclerOptions.Builder<Nota>()
                .setQuery(query,Nota.class)
                .build();
        notasAdapter=new NotasAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(notasAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void btnOnClickFabButton(View view)
    {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Intent intent=new Intent(this,AgregarNota.class);
        startActivityForResult(intent,REQUEST_CODE_AGREGAR_NOTA);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrar_sesion)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==REQUEST_CODE_AGREGAR_NOTA)
        {
            if(resultCode==RESULT_OK)
            {
                Nota nota=(Nota) data.getSerializableExtra(AgregarNota.NUEVA_NOTA);
                nota.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                dbFirsFirestore.collection(Nota.NAME_COLLECTION)
                        .add(nota)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "No se guardo la nota", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        notasAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notasAdapter.stopListening();
    }
}
