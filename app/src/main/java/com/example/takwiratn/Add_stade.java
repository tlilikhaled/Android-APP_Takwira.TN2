package com.example.takwiratn;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takwiratn.models.Stade;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Add_stade extends AppCompatActivity{

    private EditText nom_stade,addresse,h_open,h_close,prix;
    private Uri imageUri;
    private Button uploadBtn;
    private ImageView imgstadeinsert;
    private TextView textView;
    public String id_directeur,ville,type,vestiaire,eclairage;
    private RadioButton type1,type2,vestoui,vestnon,ecloui,eclnon;
    private Spinner spinner;
    private FirebaseUser user;
    private DatabaseReference reff;
    public String urlimagestade;

    FirebaseAuth fauth;
    StorageReference storagereff;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stade);

        fauth = FirebaseAuth.getInstance();
        storagereff = FirebaseStorage.getInstance().getReference();
        uploadBtn = findViewById(R.id.upload_btn);
        imgstadeinsert = findViewById(R.id.imgstadeinsert);

        spinner = (Spinner) findViewById(R.id.spinner1);
        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        vestoui = findViewById(R.id.vestoui);
        vestnon = findViewById(R.id.vestnon);
        ecloui = findViewById(R.id.ecl_oui);
        eclnon = findViewById(R.id.ecl_non);


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to load image from gallory
                Intent opengalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengalery, 1000);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==1000 && resultCode == RESULT_OK ){
            imageUri = data.getData();
            imgstadeinsert.setImageURI(imageUri);

        }
    }

    public void BtnaddStdium(View v){

        // get from form
        addresse=(EditText)findViewById(R.id.address_stade);
        if(ecloui.isChecked()){
            eclairage = ecloui.getText().toString();
        }else if (eclnon.isChecked()){
            eclairage = eclnon.getText().toString();
        }else{
            eclnon.setError("choix invalide");
            ecloui.setError("choix invalide");
        }
        h_close=(EditText)findViewById(R.id.h_close);
        h_open=(EditText)findViewById(R.id.h_open);
        id_directeur = fauth.getCurrentUser().getUid();
        nom_stade=(EditText)findViewById(R.id.nom_stade);

        prix=(EditText)findViewById(R.id.prix);
        if(type1.isChecked()){
            type = type1.getText().toString();
        }else if (type2.isChecked()){
            type = type2.getText().toString();
        }else{
            type1.setError("choix invalide");
            type2.setError("choix invalide");
        }
        if(vestoui.isChecked()){
            vestiaire = vestoui.getText().toString();
        }else if (vestnon.isChecked()){
            vestiaire = vestnon.getText().toString();
        }else{
            vestoui.setError("choix invalide");
            vestnon.setError("choix invalide");
        }
        ville = spinner.getSelectedItem().toString();


        final String addressehome = addresse.getText().toString();
        final String closehour = h_close.getText().toString();
        final String openhour = h_open.getText().toString();
        final String nomstade = nom_stade.getText().toString();
        final String prixxx = prix.getText().toString();


        final StorageReference filereff = storagereff.child("Stade/"+fauth.getCurrentUser().getUid()+"/"+nom_stade.getText().toString()+"/stadeimg.jpg");
        filereff.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filereff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imgstadeinsert);
                        urlimagestade = uri.toString();
                        Stade stade = new Stade(addressehome,eclairage,closehour,openhour,id_directeur,nomstade,urlimagestade,prixxx,type,vestiaire,ville);
                        FirebaseDatabase.getInstance().getReference("Stade")
                                .child(nomstade)
                                .setValue(stade).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Add_stade.this, "Ajout complète", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(Add_stade.this, "n'a pas réussi à ajouter", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_stade.this,"Image Non téléchargée ",Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(Add_stade.this,"Image téléchargée ",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Add_stade.this,"Errer de téléchargée d'image",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
