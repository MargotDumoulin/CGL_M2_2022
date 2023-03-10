package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public class Apporteur {
    private Long id;
    private boolean affilie;
    private String nom;
    private String prenom;
    private Apporteur parrain;
    private boolean isDeleted;
    private Double totalCommissionsMCourant;
    private Double totalCommissionsMM1;
    private Double totalCommissionsMM2;

    public Apporteur(
            Long id,
            boolean affilie,
            String nom,
            String prenom,
            Apporteur parrain,
            boolean isDeleted) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = parrain;
        this.isDeleted = isDeleted;
        this.totalCommissionsMCourant = 0.0;
        this.totalCommissionsMM1 = 0.0;
        this.totalCommissionsMM2 = 0.0;
    }

    public Apporteur(
            Long id,
            boolean affilie,
            String nom,
            String prenom,
            boolean isDeleted,
            Double totalCommissionsMCourant,
            Double totalCommissionsMM1,
            Double totalCommissionsMM2) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = null;
        this.isDeleted = isDeleted;
        this.totalCommissionsMCourant = totalCommissionsMCourant;
        this.totalCommissionsMM1 = totalCommissionsMM1;
        this.totalCommissionsMM2 = totalCommissionsMM2;
    }

    public Apporteur(Long id,
                     boolean affilie,
                     String nom,
                     String prenom,
                     boolean isDeleted) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = null;
        this.isDeleted = isDeleted;
        this.totalCommissionsMCourant = 0.0;
        this.totalCommissionsMM1 = 0.0;
        this.totalCommissionsMM2 = 0.0;
    }
}
