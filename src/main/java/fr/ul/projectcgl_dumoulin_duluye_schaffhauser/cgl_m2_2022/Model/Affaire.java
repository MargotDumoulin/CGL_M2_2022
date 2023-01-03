package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Affaire {
    private int id;
    private Apporteur apporteur;
    private Date date;
    private float commission;
    
    // Liste avec id de l'apporteur et montant de la commission associée 
    private Map<Integer, Double> commissionsPerso;

    public Affaire(int id, Apporteur apporteur, Date date, float commission, Map<Integer, Double> commissionsPerso) {
        this.id = id;
        this.apporteur = apporteur;
        this.date = date;
        this.commission = commission;
        this.commissionsPerso = commissionsPerso;
    }

    public Affaire(int id, Apporteur apporteur, Date date, float commission) {
        this.id = id;
        this.apporteur = apporteur;
        this.date = date;
        this.commission = commission;
        this.commissionsPerso = new HashMap<Integer, Double>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Apporteur getApporteur() {
        return this.apporteur;
    }

    public void setApporteur(Apporteur apporteur) {
        this.apporteur = apporteur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public Map<Integer, Double> getCommissionsPerso() {
        return commissionsPerso;
    }

    public void setCommissionsPerso(Map<Integer, Double> commissionsPerso) {
        this.commissionsPerso = commissionsPerso;
    }
}
