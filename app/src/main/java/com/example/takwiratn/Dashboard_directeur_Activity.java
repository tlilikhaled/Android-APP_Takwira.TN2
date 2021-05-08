package com.example.takwiratn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard_directeur_Activity extends AppCompatActivity {

    CardView profil;
    CardView reservation;
    CardView stade;
    CardView add_stade;
    CardView logout;
    private FirebaseUser user;
    private DatabaseReference reff;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_directeur);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();
        profil = findViewById(R.id.profil);
        reservation = findViewById(R.id.reservation);
        stade = findViewById(R.id.stade);
        add_stade = findViewById(R.id.add_stade);
        logout = findViewById(R.id.logout);

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Dashboard_directeur_Activity.this, Profil_Directeur.class);
                startActivity(in);

            }
        });
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Dashboard_directeur_Activity.this, ReservStadedirecteurActivity.class);
                startActivity(in);
            }
        });
        stade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Dashboard_directeur_Activity.this, CollStadeActivity.class);
                startActivity(in);
            }
        });

        add_stade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_directeur_Activity.this, Add_stade.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Dashboard_directeur_Activity.this, MainActivity.class);
                startActivity(in);
            }
        });
    }



}