package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Commission")
@Table(name = "commission")
public class CommissionEntity implements IEntity<CommissionEntityId> {

    @EmbeddedId
    private CommissionEntityId id;

    private Double montant;
}
