package com.example.takwiratn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takwiratn.models.Booking;
import com.example.takwiratn.models.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Adapter_reserv_directeur extends FirebaseRecyclerAdapter<Booking, Adapter_reserv_directeur.myviewholder>
{
    private FirebaseUser user;
    private DatabaseReference referance;
    private DatabaseReference ref;
    private String UserId;
    public Adapter_reserv_directeur(@NonNull FirebaseRecyclerOptions<Booking> options) {
        super(options);
    }




    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Booking model) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        referance = FirebaseDatabase.getInstance().getReference("Booking");
        ref  = FirebaseDatabase.getInstance().getReference("Users");
        UserId = user.getUid();
        if(UserId.equals(model.getId_directeur())) {
            holder.namestadetext.setText(model.getStade_name());
            holder.dateresrvtext.setText(model.getDate_order());
            holder.nbrpertext.setText(model.getNbr_total_personne());
            holder.prixtttext.setText(model.getPrix_total_order());
            ref.child(model.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userinfo = snapshot.getValue(User.class);
                    if(userinfo != null){
                        holder.nameclienttext.setText(userinfo.getFullname());
                        holder.telclienttext.setText(userinfo.getPhone());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{

            holder.card.setVisibility(View.INVISIBLE);
        }




        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.namestadetext.getContext());
                builder.setTitle("Supprimer Réservation");
                builder.setMessage("Vous êtes sûr ...?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Booking")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesignreservdirec,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        CardView card;
        ImageView img;
        TextView namestadetext,dateresrvtext,nbrpertext,prixtttext,nameclienttext,telclienttext;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            img=itemView.findViewById(R.id.img);
            namestadetext=itemView.findViewById(R.id.namestadetext);
            dateresrvtext=itemView.findViewById(R.id.dateresrvtext);
            nbrpertext=itemView.findViewById(R.id.nbrpertext);
            prixtttext=itemView.findViewById(R.id.prixtttext);
            telclienttext=itemView.findViewById(R.id.telclienttext);
            nameclienttext=itemView.findViewById(R.id.nameclienttext);
        }
    }

}
