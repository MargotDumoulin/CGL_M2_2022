package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import java.util.ArrayList;
import java.util.List;

public class Apporteur {
    private int id;
    private boolean affilie;
    private String nom;
    private String prenom;
    private List<Apporteur> parrains;

    public Apporteur(int id, boolean affilie, String nom, String prenom) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrains = new ArrayList<Apporteur>();
    }

    public Apporteur(int id, boolean affilie, String nom, String prenom, List<Apporteur> parrains) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrains = parrains;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAffilie() {
        return affilie;
    }

    public void setAffilie(boolean affilie) {
        this.affilie = affilie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<Apporteur> getParrains() {
        return parrains;
    }

    public void setParrains(List<Apporteur> parrains) {
        this.parrains = parrains;
    }



}
