package com.example.takwiratn.models;

public class Stade {
    private String id_directeur,nom_stade,addresse,ville,type,vestiaire,eclairage,h_open,h_close,prix,pimage;
public Stade(){}

    public Stade(String addresse,String eclairage,String h_close ,String h_open, String id_directeur, String nom_stade, String pimage, String prix,String type,String vestiaire,String ville) {
        this.id_directeur = id_directeur;
        this.nom_stade = nom_stade;
        this.addresse = addresse;
        this.ville = ville;
        this.type = type;
        this.vestiaire = vestiaire;
        this.eclairage = eclairage;
        this.h_open = h_open;
        this.h_close = h_close;
        this.prix = prix;
        this.pimage = pimage;
    }

    public String getNom_stade() {
        return nom_stade;
    }

    public void setNom_stade(String nom_stade) {
        this.nom_stade = nom_stade;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVestiaire() {
        return vestiaire;
    }

    public void setVestiaire(String vestiaire) {
        this.vestiaire = vestiaire;
    }

    public String getEclairage() {
        return eclairage;
    }

    public void setEclairage(String eclairage) {
        this.eclairage = eclairage;
    }

    public String getH_open() {
        return h_open;
    }

    public void setH_open(String h_open) {
        this.h_open = h_open;
    }

    public String getH_close() {
        return h_close;
    }

    public void setH_close(String h_close) {
        this.h_close = h_close;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getId_directeur() {
        return id_directeur;
    }

    public void setId_directeur(String id_directeur) {
        this.id_directeur = id_directeur;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
