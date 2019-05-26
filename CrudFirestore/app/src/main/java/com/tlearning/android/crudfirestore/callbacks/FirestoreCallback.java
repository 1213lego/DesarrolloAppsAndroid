package com.tlearning.android.crudfirestore.callbacks;

public interface FirestoreCallback<T> {
    void onSuccess(T t);
    void onFailure(Exception exception);
}
