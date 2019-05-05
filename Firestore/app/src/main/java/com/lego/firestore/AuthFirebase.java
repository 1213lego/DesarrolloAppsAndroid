package com.lego.firestore;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class AuthFirebase extends AppCompatActivity
{
    private EditText email;
    private EditText password;
    private FirebaseAuth firebaseAuth;
    private Button btnRegistrarse;
    private Button btnIniciaSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_firebase);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        firebaseAuth=FirebaseAuth.getInstance();
        btnRegistrarse =findViewById(R.id.btnSignIn);
        btnIniciaSesion =findViewById(R.id.btnSignUp);
    }

    public void btnSignUp(View view)
    {
        if(isValidFields())
        {
            showProgressDialog();
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                updatdeUI(firebaseAuth.getCurrentUser());
                            }
                            else {
                                Toast.makeText(AuthFirebase.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                            hideProgressDialog();
                        }
                    });
        }
    }
    public void btnSignIn(View view)
    {
        if(isValidFields())
        {
            showProgressDialog();
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(AuthFirebase.this, "Registro exitoso, inicie sesion", Toast.LENGTH_SHORT).show();
                                btnRegistrarse.setEnabled(false);
                            }
                            else
                            {
                                Toast.makeText(AuthFirebase.this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                            }
                            hideProgressDialog();
                        }
                    });
        }
    }
    public boolean validPassword()
    {
        if(!TextUtils.isEmpty(password.getText()) && password.getText().length()>=6)
        {
            password.setError(null);
            return true;
        }
        else
        {
            password.setError("La contraseña debe tener mas de 6 caracteres");
            return false;
        }
    }
    public boolean validEmail()
    {
        if(!TextUtils.isEmpty(email.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
        {
            email.setError(null);
            return true;
        }
        else
        {
            email.setError("Email invalido");
            return false;
        }
    }
    public boolean isValidFields()
    {
        return validEmail() && validPassword();
    }
    private void updatdeUI(FirebaseUser firebaseUser)
    {
        if(firebaseUser!=null)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        updatdeUI(FirebaseAuth.getInstance().getCurrentUser());
    }

    @VisibleForTesting
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
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
