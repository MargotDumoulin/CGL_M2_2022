package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Affaire {
    private int id;
    private Apporteur apporteur;
    private Date date;
    private float commission;
    
    // Liste avec id de l'apporteur et montant de la commission associée 
    private Map<Integer, Double> commissionsPerso;

    public Affaire(int id, Apporteur apporteur, Date date, float commission) {
        this.id = id;
        this.apporteur = apporteur;
        this.date = date;
        this.commission = commission;
        this.commissionsPerso = new HashMap<>();
    }
}
