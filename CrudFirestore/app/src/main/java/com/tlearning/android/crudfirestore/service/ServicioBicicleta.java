package com.tlearning.android.crudfirestore.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.tlearning.android.crudfirestore.callbacks.FirestoreCallback;
import com.tlearning.android.crudfirestore.model.Bicicleta;

public class ServicioBicicleta {
    private static final String TAG = "ServicioBicicleta";
    private static ServicioBicicleta instance;
    private CollectionReference bicicletasCollecionRef;

    private ServicioBicicleta(){
        bicicletasCollecionRef =FirebaseFirestore.getInstance().collection(Bicicleta.COLLECTION_NAME);
    }

    public static ServicioBicicleta getInstance(){
        return instance == null ? new ServicioBicicleta() : instance;
    }

    public void buscarBicicleta(Bicicleta bicicleta,final FirestoreCallback<Bicicleta> firestoreCallback)
    {
        bicicletasCollecionRef.document(bicicleta.getSerial())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists())
                    {
                        firestoreCallback.onSuccess(task.getResult().toObject(Bicicleta.class));
                    }
                    else
                        {
                        firestoreCallback.onSuccess(null);
                    }
                }
                else
                    {
                    firestoreCallback.onFailure(task.getException());
                }
            }
        });
    }
    public void guardarBicicleta(final Bicicleta bike, final FirestoreCallback<Bicicleta> firestoreCallback){
       buscarBicicleta(bike, new FirestoreCallback<Bicicleta>()
       {
           @Override
           public void onSuccess(Bicicleta bicicleta)
           {
               if(bicicleta==null)
               {
                   bicicletasCollecionRef.document(bike.getSerial())
                           .set(bike)
                           .addOnCompleteListener(new OnCompleteListener<Void>()
                           {
                               @Override
                               public void onComplete(@NonNull Task<Void> task)
                               {
                                   if(task.isSuccessful()){
                                       firestoreCallback.onSuccess(null);
                                   }
                                   else
                                       {
                                       firestoreCallback.onFailure(task.getException());
                                   }
                               }
                           });
               }
               else {
                   firestoreCallback.onFailure(new Exception("La bicicleta con el serial " + bike.getSerial() + " ya existe"));
               }
           }
           @Override
           public void onFailure(Exception exception)
           {
               firestoreCallback.onFailure(exception);
           }
       });
    }
    public void actualizarBicicle(Bicicleta bicicleta,final FirestoreCallback<Bicicleta> firestoreCallback)
    {
        bicicletasCollecionRef.document(bicicleta.getSerial())
                .set(bicicleta)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firestoreCallback.onSuccess(null);
                        }
                        else {
                            firestoreCallback.onFailure(task.getException());
                        }
                    }
                });
    }
    public void eliminarBicicleta(Bicicleta bicicleta, final FirestoreCallback<Bicicleta> firestoreCallback){
        bicicletasCollecionRef.document(bicicleta.getSerial()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            firestoreCallback.onSuccess(null);
                        }
                        else
                        {
                            firestoreCallback.onFailure(task.getException());
                        }
                    }
                });
    }

    public void deleteAll(final FirestoreCallback<Bicicleta> firestoreCallback){
        bicicletasCollecionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Void>() {
                        @Nullable
                        @Override
                        public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                transaction.delete(document.getReference());
                            }
                            return null;
                        }
                    });
                }
                else {
                    firestoreCallback.onFailure(task.getException());
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    firestoreCallback.onSuccess(null);
                }
                else {
                    firestoreCallback.onFailure(task.getException());
                }
            }
        });

    }
    public Query darQueryBicicletas(){
        return bicicletasCollecionRef;
    }
}
