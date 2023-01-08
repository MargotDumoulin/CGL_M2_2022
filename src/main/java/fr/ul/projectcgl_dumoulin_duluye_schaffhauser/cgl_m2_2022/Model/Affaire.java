package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Affaire {
    private Long id;
    private Apporteur apporteur;
    private Date date;
    private Double commissionGlobale;

    // Liste avec id de l'apporteur et montant de la commission associée 
    private List<CommissionPerso> commissions;

    public Affaire(Long id, Apporteur apporteur, Date date, Double commissionGlobale) {
        this.id = id;
        this.apporteur = apporteur;
        this.date = date;
        this.commissionGlobale = commissionGlobale;
    }
}
