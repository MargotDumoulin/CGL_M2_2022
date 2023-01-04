package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CommissionPerso")
@Table(name = "commission_perso")
public class CommissionPersoEntity {

    @EmbeddedId
    private CommissionPersoEntityId id;

    private Double montant;
}
