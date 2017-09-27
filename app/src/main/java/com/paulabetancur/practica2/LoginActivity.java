package com.paulabetancur.practica2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private String correoR, contrasenaR;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private EditText eCorreo, eContrasena;
    private String Correo, Contrasena;
    private int RC_SIGN_IN = 5678;
    private int optlog;

    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eContrasena = (EditText) findViewById(R.id.eContrasena);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            correoR = extras.getString("correo");
            contrasenaR = extras.getString("contrasena");
        }


        //Login con google

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Error en login", Toast.LENGTH_SHORT).show();
                    }
                })

                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                signIn();
            }
        });




        //Login con facebook

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
                goMainActivity();
                optlog=1;

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login cancelado", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error en login", Toast.LENGTH_SHORT).show();

            }
        });




        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.paulabetancur.practica2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println(Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void goMainActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("correo", correoR);
        intent.putExtra("contrasena", contrasenaR);
        intent.putExtra("optlog", optlog);
        startActivity(intent);
        finish();
    }


    public void iniciar(View view) {

        Correo = eCorreo.getText().toString();
        Contrasena = eContrasena.getText().toString();
        optlog=3;

        if(Correo.equals(correoR) && Contrasena.equals(contrasenaR)){
            goMainActivity();
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1234 && resultCode == RESULT_OK) { //Registro
            correoR = data.getExtras().getString("correo");
            contrasenaR = data.getExtras().getString("contrasena");
            Log.d("correo", correoR);
            Log.d("contrasena", contrasenaR);

        } else if (requestCode == RC_SIGN_IN) { //google
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        } else { //Login con facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("google", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            optlog=2;
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("nombre de usuario: ", acct.getDisplayName());
            Toast.makeText(getApplicationContext(), "Login exitoso", Toast.LENGTH_SHORT).show();
            goMainActivity();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Error en login", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrese(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivityForResult(intent, 1234);
    }
}
