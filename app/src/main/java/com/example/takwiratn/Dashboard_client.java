package com.example.takwiratn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takwiratn.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard_client extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reff;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_client);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();

        final TextView  fname = (TextView) findViewById(R.id.txt_value_fname);
        final TextView  email = (TextView) findViewById(R.id.txt_value_email);
        final TextView  phone = (TextView) findViewById(R.id.txt_value_phone);

        reff.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofil = snapshot.getValue(User.class);
                if(userprofil != null){
                    String name2 = userprofil.fullname;
                    String email2 = userprofil.email;
                    String phone2 = userprofil.phone;
                    fname.setText(name2);
                    email.setText(email2);
                    phone.setText(phone2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Dashboard_client.this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Logout(View v){
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}