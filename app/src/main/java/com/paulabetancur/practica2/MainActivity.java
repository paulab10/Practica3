package com.paulabetancur.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {


private String correoR, contrasenaR;
    private String correo, contrasena;
    private int optlog;
    GoogleApiClient mGoogleApiClient;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        Intent intent;

        switch (id){
            case R.id.mPerfil:
                break;
            case R.id.mCerrar:

                if (optlog==1){
                    LoginManager.getInstance().logOut();
                    Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else if(optlog==2){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                }
                            });
                } else if(optlog==3){
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("correo", correoR);
                    intent.putExtra("contrasena", contrasenaR);
                    startActivity(intent);
                    finish();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Bundle extras = getIntent().getExtras();
       correoR = extras.getString("correo");
       contrasenaR = extras.getString("contrasena");
       optlog = extras.getInt("optlog");
    }

    public void cargarp(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
        intent.putExtra("correo", correoR);
        intent.putExtra("contrasena", contrasenaR);
        startActivity(intent);
    }
}
