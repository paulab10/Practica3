package com.paulabetancur.practica2;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.attr.data;

public class PerfilActivity extends AppCompatActivity {

    //private String correo, contrasena;
    private String correoR, contrasenaR;
    private TextView tNombre, tContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tNombre = (TextView) findViewById(R.id.tNombre);
        tContrasena = (TextView) findViewById(R.id.tContrasena);


        Bundle extras = getIntent().getExtras();
        correoR = extras.getString("correo");
        contrasenaR = extras.getString("contrasena");
        tNombre.setText(correoR);
        tContrasena.setText(contrasenaR);


    }
}
