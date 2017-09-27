package com.paulabetancur.practica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {


    private String correo, contrasena, contrasenaR;
    EditText eCorreo, eContrasena, eRepcontrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);
        eRepcontrasena = (EditText) findViewById(R.id.eRepcontrasena);

    }

    public void registrar(View view) {
        correo = eCorreo.getText().toString();
        contrasena = eContrasena.getText().toString();
        contrasenaR = eRepcontrasena.getText().toString();


        if (correo.equals("") || contrasena.equals("")){
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }

        else if( !contrasena.equals(contrasenaR)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

        else {
            Intent intent = new Intent();
            intent.putExtra("correo", correo);
            intent.putExtra("contrasena", contrasena);
            setResult(RESULT_OK, intent);
            finish();
        }


    }
}
