package com.example.takwiratn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takwiratn.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profil_Directeur extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reff;
    private String UserId;
    FrameLayout edit_profil;
    FrameLayout back_to_dash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_directeur);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();

        final TextView  fname = (TextView) findViewById(R.id.txt_value_fname);
        final TextView  fullname = (TextView) findViewById(R.id.txt_value_fullname);
        final TextView  email = (TextView) findViewById(R.id.txt_value_email);
        final TextView  phone = (TextView) findViewById(R.id.txt_value_phone);

        edit_profil = findViewById(R.id.edit_profil);
        back_to_dash = findViewById(R.id.back_to_dash);

        reff.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofil = snapshot.getValue(User.class);
                if(userprofil != null){
                    String name2 = userprofil.fullname;
                    String email2 = userprofil.email;
                    String phone2 = userprofil.phone;
                    fullname.setText(name2);
                    fname.setText(name2);
                    email.setText(email2);
                    phone.setText(phone2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Profil_Directeur.this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        });

        back_to_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Profil_Directeur.this, Dashboard_directeur_Activity.class);
                startActivity(in);

            }
        });
        edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Profil_Directeur.this, Edit_Profil_Directeur.class);
                startActivity(in);

            }
        });

    }



}