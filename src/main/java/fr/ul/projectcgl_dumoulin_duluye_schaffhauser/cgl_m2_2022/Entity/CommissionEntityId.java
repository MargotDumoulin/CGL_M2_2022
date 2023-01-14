package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CommissionEntityId implements Serializable {
    @ManyToOne
    private AffaireEntity affaire;
    @ManyToOne
    private ApporteurEntity apporteur;
}
