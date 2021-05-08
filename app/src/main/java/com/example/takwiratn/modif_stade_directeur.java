package com.example.takwiratn;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class modif_stade_directeur extends Fragment {
    private FirebaseUser user;
    private DatabaseReference referance;
    private String UserId;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String id_directeur, nom_stade, addresse, ville, pimage, type, vestiaire, eclairage, h_open, h_close, prix;

    public modif_stade_directeur() {


    }

    @SuppressLint("ValidFragment")
    public modif_stade_directeur(String id_directeur, String nom_stade, String addresse, String ville, String pimage, String type, String vestiaire, String eclairage, String h_open, String h_close, String prix) {
        this.nom_stade = nom_stade;
        this.addresse = addresse;
        this.ville = ville;
        this.pimage = pimage;
        this.type = type;
        this.vestiaire = vestiaire;
        this.eclairage = eclairage;
        this.h_open = h_open;
        this.h_close = h_close;
        this.prix = prix;
        this.id_directeur = id_directeur;
    }


    public static modif_stade_directeur newInstance(String param1, String param2) {
        modif_stade_directeur fragment = new modif_stade_directeur();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        referance = FirebaseDatabase.getInstance().getReference("Stade");
        UserId = user.getUid();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.editstadedirecteur, container, false);

        ImageView imageholder = view.findViewById(R.id.imagegholder);
         EditText nameholder = view.findViewById(R.id.nameholder);
        EditText addresseholder = view.findViewById(R.id.addresseholder);
        TextView villeholder = view.findViewById(R.id.villeholder);
        TextView typeholder = view.findViewById(R.id.typeholder);
        TextView vestholder = view.findViewById(R.id.vestholder);
        TextView eclholder = view.findViewById(R.id.eclholder);
        TextView openholder = view.findViewById(R.id.openholder);
        TextView closeholder = view.findViewById(R.id.closeholder);
        TextView prixholder = view.findViewById(R.id.prixholder);
        FrameLayout confirme_stade = view. findViewById(R.id.confirme_stade);
        nameholder.setText(nom_stade);
        addresseholder.setText(addresse);
        villeholder.setText(ville);
        typeholder.setText(type);
        vestholder.setText(vestiaire);
        eclholder.setText(eclairage);
        openholder.setText(h_open);
        closeholder.setText(h_close);
        prixholder.setText(prix);
        Glide.with(getContext()).load(pimage).into(imageholder);




        return view;
    }



}