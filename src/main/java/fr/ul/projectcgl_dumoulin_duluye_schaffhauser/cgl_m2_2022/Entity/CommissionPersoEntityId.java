package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class CommissionPersoEntityId implements Serializable {
    @ManyToOne
    private AffaireEntity affaire;
    @ManyToOne
    private ApporteurEntity apporteur;
}
