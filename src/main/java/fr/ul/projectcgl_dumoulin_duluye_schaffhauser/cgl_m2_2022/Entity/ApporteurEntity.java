package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Apporteur")
@Table(name = "apporteur")
public class ApporteurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @OneToOne
    private ApporteurEntity parrain;

    /*@OneToMany(mappedBy = "apporteur")
    @JoinColumn(table = "AFFAIRE", referencedColumnName = "ID_APPORTEUR")
    private List<AffaireEntity> affaires;*/
}
