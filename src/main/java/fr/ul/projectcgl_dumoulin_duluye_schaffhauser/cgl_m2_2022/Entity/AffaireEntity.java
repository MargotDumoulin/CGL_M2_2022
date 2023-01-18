package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Affaire")
@Table(name = "affaire")
public class AffaireEntity implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ApporteurEntity apporteur;

    private Date date;

    @Column(name = "COMMISSION_GLOBALE")
    private Double commissionGlobale;
}
