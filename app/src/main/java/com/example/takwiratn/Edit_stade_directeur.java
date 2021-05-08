package com.example.takwiratn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_stade_directeur extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference referance;
    private String UserId;
    EditText nameholder,addresseholder;
    FrameLayout confirme_stade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editstadedirecteur);
        Intent data = getIntent();
        String nom_stade = data.getStringExtra("nom_stade");
        String addresse = data.getStringExtra("addresse");
        String ville = data.getStringExtra("ville");
        user = FirebaseAuth.getInstance().getCurrentUser();
        referance = FirebaseDatabase.getInstance().getReference("Stade");
        UserId = user.getUid();
        nameholder = (EditText) findViewById(R.id.nameholder);
        addresseholder = (EditText) findViewById(R.id.addresseholder);
        confirme_stade =  findViewById(R.id.confirme_stade);
        nameholder.setText(nom_stade);
        addresseholder.setText(addresse);

        confirme_stade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameholder.getText().toString().isEmpty() ) {
                    Toast.makeText(Edit_stade_directeur.this, "Remplir Email ", Toast.LENGTH_LONG).show();
                    return;
                }
                if ( addresseholder.getText().toString().isEmpty()) {
                    Toast.makeText(Edit_stade_directeur.this, "Remplier telephone", Toast.LENGTH_LONG).show();
                    return;
                }
                user.updateEmail(nameholder.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        referance.child(user.getUid()).child("nom_stade").setValue(nameholder.getText().toString());
                        referance.child(user.getUid()).child("addresse").setValue(addresseholder.getText().toString());
                        Toast.makeText(Edit_stade_directeur.this, "Votre profil a été modifié", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(Edit_stade_directeur.this, Profil_Directeur.class);
                        startActivity(in);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Edit_stade_directeur.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });




            }
        });

    }
}
