package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private ProgressBar progresbar;
    private Button btn_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.email =  (EditText) findViewById(R.id.id_user);
        this.password = (EditText) findViewById(R.id.id_pass);
        this.progresbar = (ProgressBar) findViewById(R.id.progressBar);
        this.btn_log = (Button) findViewById(R.id.id_btn_log);

        mAuth = FirebaseAuth.getInstance();

    }

    public void BtnLoginAction(View v){
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        // Validation of form
        if(em.matches("")){
            email.setError("Vous n'avez pas entré votre email");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(em).matches()){
            email.setError("Vous n'avez pas entré valid email");
            email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Vous n'avez pas entré de Mot de passe");
            password.requestFocus();
            return;
        }

        // Conx to DataBase & Authentification
        progresbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    progresbar.setVisibility(View.GONE);
                    Intent in = new Intent(MainActivity.this, Dashboard_directeur_Activity.class);
                    startActivity(in);


                }else{
                    Toast.makeText(MainActivity.this, "Identifiant invalide", Toast.LENGTH_SHORT).show();
                    email.setError("Revérifier");
                    password.setError("Revérifier");
                    email.requestFocus();
                    progresbar.setVisibility(View.GONE);
                }
            }
        });




    }

    public void TxtGoToInscriAction(View v){

        // to go to next activity
        Intent intent = new Intent(this, InscriActivity.class);
        startActivity(intent);
    }

    public void TxtGoToForgetPassword(View v){
        Intent forget = new Intent(MainActivity.this, ForgetPassword.class);
        startActivity(forget);
    }

}
