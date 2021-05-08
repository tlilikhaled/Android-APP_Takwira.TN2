package com.example.takwiratn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.takwiratn.models.Stade;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Adapter_directeur extends FirebaseRecyclerAdapter<Stade,Adapter_directeur.myviewholder>
{
    private FirebaseUser user;
    private DatabaseReference referance;
    private String UserId;
    public Adapter_directeur(@NonNull FirebaseRecyclerOptions<Stade> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Stade model) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        referance = FirebaseDatabase.getInstance().getReference("Stade");
        UserId = user.getUid();
        if(UserId.equals(model.getId_directeur())) {
            holder.nametext.setText(model.getNom_stade());
            holder.addressetext.setText(model.getAddresse());
            holder.villetext.setText(model.getVille());
            Glide.with(holder.img1.getContext()).load(model.getPimage()).into(holder.img1);

        }else{

            holder.card.setVisibility(View.INVISIBLE);
        }
            holder.img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.wrapper, new Descfrag_directeur(model.getId_directeur(), model.getNom_stade(), model.getAddresse(), model.getVille(), model.getPimage(), model.getType(), model.getVestiaire(), model.getEclairage(), model.getH_open(), model.getH_close(), model.getPrix())).addToBackStack(null).commit();
                }
            });

           holder.img2.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                   final DialogPlus dialogPlus=DialogPlus.newDialog(holder.nametext.getContext())
                           .setContentHolder(new ViewHolder(R.layout.editstadedirecteur))
                           .setExpanded(true,1100)
                           .create();

                   View myview=dialogPlus.getHolderView();

                   final EditText nom_stade = myview.findViewById(R.id.nameholder);
                   final EditText addresse = myview.findViewById(R.id.addresseholder);
                   final EditText ville = myview.findViewById(R.id.villeholder);
                   final EditText type = myview.findViewById(R.id.typeholder);
                   final EditText vestiaire=myview.findViewById(R.id.vestholder);
                   final EditText eclairage=myview.findViewById(R.id.eclholder);
                   final EditText h_open=myview.findViewById(R.id.openholder);
                   final EditText prix=myview.findViewById(R.id.prixholder);
                   final EditText h_close=myview.findViewById(R.id.closeholder);
                   FrameLayout confirme_stade=myview.findViewById(R.id.confirme_stade);

                         nom_stade.setText(model.getNom_stade());
                         addresse.setText(model.getAddresse());
                         ville.setText(model.getVille());
                         type.setText(model.getType());
                         vestiaire.setText(model.getVestiaire());
                         eclairage.setText(model.getEclairage());
                         h_open.setText(model.getH_open());
                         h_close.setText(model.getH_close());
                         prix.setText(model.getPrix());

                   dialogPlus.show();

                   confirme_stade.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View view) {
                       Map<String,Object> map=new HashMap<>();
                       map.put("nom_stade",nom_stade.getText().toString());
                       map.put("addresse",addresse.getText().toString());
                       map.put("ville",ville.getText().toString());
                       map.put("type",type.getText().toString());
                       map.put("vestiaire",vestiaire.getText().toString());
                       map.put("eclairage",eclairage.getText().toString());
                       map.put("h_open",h_open.getText().toString());
                       map.put("h_close",h_close.getText().toString());
                       map.put("prix",prix.getText().toString());

                       FirebaseDatabase.getInstance().getReference().child("Stade")
                               .child(getRef(position).getKey()).updateChildren(map)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid) {
                                       dialogPlus.dismiss();
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       dialogPlus.dismiss();
                                   }
                               });
                   }
                                });


               }
           });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.nametext.getContext());
                builder.setTitle("Supprimer Stade");
                builder.setMessage("Vous êtes sûr ...?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Stade")
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        CardView card;
        ImageView img1,img2,img3;
        TextView nametext,addressetext,villetext;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            img1=itemView.findViewById(R.id.img1);
            img2=itemView.findViewById(R.id.img2);
            img3=itemView.findViewById(R.id.img3);
            nametext=itemView.findViewById(R.id.nametext);
            addressetext=itemView.findViewById(R.id.addressetext);
            villetext=itemView.findViewById(R.id.villetext);
        }
    }

}
