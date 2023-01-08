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
    private Double totalCommissionsMCourant;
    private Double totalCommissionsMM1;
    private Double totalCommissionsMM2;

    public Apporteur(
            Long id,
            boolean affilie,
            String nom,
            String prenom,
            Double totalCommissionsMCourant,
            Double totalCommissionsMM1,
            Double totalCommissionsMM2
    ) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = null;
        this.totalCommissionsMCourant = totalCommissionsMCourant;
        this.totalCommissionsMM1 = totalCommissionsMM1;
        this.totalCommissionsMM2 = totalCommissionsMM2;
    }

    public Apporteur(Long id, boolean affilie, String nom, String prenom) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = null;
        this.totalCommissionsMCourant = 0.0;
        this.totalCommissionsMM1 = 0.0;
        this.totalCommissionsMM2 = 0.0;
    }
}
