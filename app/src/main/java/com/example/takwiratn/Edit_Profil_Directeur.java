package com.example.takwiratn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_Profil_Directeur extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference referance;
    FirebaseAuth fauth;
    private String UserId;
    EditText mod_fullname,mod_email,mod_phone,mod_pass;
    TextView mod_fname;
    FrameLayout confirme_profil;
    FrameLayout back_to_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_directeur);
        Intent data = getIntent();
        String fullname = data.getStringExtra("fullname");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");
        user = FirebaseAuth.getInstance().getCurrentUser();
        referance = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();
          mod_fname = findViewById(R.id.mod_fname);
          mod_fullname = (EditText) findViewById(R.id.mod_fullname);
          mod_email = (EditText) findViewById(R.id.mod_email);
          mod_phone = (EditText) findViewById(R.id.mod_phone);
        mod_pass = (EditText) findViewById(R.id.mod_pass);
        mod_fname.setText(fullname);
        mod_fullname.setText(fullname);
        mod_email.setText(email);
        mod_phone.setText(phone);
        confirme_profil = findViewById(R.id.confirme_profil);
        back_to_profil = findViewById(R.id.back_to_profil);
        back_to_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Edit_Profil_Directeur.this, Profil_Directeur.class);
                startActivity(in);

            }
        });

        confirme_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mod_email.getText().toString().isEmpty() ) {
                    Toast.makeText(Edit_Profil_Directeur.this, "Remplir Email ", Toast.LENGTH_LONG).show();
                    return;
                }
                if ( mod_phone.getText().toString().isEmpty()) {
                    Toast.makeText(Edit_Profil_Directeur.this, "Remplier telephone", Toast.LENGTH_LONG).show();
                    return;
                }
                user.updateEmail(mod_email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        referance.child(user.getUid()).child("username").setValue(mod_fullname.getText().toString());
                        referance.child(user.getUid()).child("fullname").setValue(mod_fullname.getText().toString());
                        referance.child(user.getUid()).child("email").setValue(mod_email.getText().toString());
                        referance.child(user.getUid()).child("phone").setValue(mod_phone.getText().toString());
                        if (!mod_pass.getText().toString().equals("")) {
                            referance.child(user.getUid()).child("password").setValue(mod_pass.getText().toString());
                            Toast.makeText(Edit_Profil_Directeur.this, "Remplier mot de passe", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(Edit_Profil_Directeur.this, "Votre profil a été modifié", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Edit_Profil_Directeur.this, Profil_Directeur.class);
                        startActivity(in);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Edit_Profil_Directeur.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });




            }
        });
    }


}


