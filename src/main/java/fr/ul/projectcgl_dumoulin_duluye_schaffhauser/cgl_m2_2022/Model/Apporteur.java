package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Apporteur {
    private Long id;
    private boolean affilie;
    private String nom;
    private String prenom;
    private Apporteur parrain;
    private List<CommissionPerso> commissionsMCourant;
    private List<CommissionPerso> commissionsMMoins1;
    private List<CommissionPerso> commissionsMMoins2;

    public Apporteur(Long id, boolean affilie, String nom, String prenom) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = null;
        commissionsMCourant = new ArrayList<>();
        commissionsMMoins1 = new ArrayList<>();
        commissionsMMoins2 = new ArrayList<>();
    }
}
