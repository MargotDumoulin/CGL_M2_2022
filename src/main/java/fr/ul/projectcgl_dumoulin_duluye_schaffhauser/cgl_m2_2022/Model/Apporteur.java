package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Apporteur {
    private Long id;
    private Boolean affilie;
    private String nom;
    private String prenom;
    private Apporteur parrain;
    private List<Affaire> affaires;

    public Apporteur(Long id, Boolean affilie, String nom, String prenom) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrain = null;
    }
}
