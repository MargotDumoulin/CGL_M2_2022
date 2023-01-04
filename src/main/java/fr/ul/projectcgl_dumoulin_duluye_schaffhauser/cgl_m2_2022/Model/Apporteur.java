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
@NoArgsConstructor
public class Apporteur {
    private int id;
    private boolean affilie;
    private String nom;
    private String prenom;
    private List<Apporteur> parrains;

    public Apporteur(int id, boolean affilie, String nom, String prenom) {
        this.id = id;
        this.affilie = affilie;
        this.nom = nom;
        this.prenom = prenom;
        this.parrains = new ArrayList<>();
    }
}
