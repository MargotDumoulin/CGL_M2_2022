package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Setting")
@Table(name = "parametres")
public class SettingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;
    @Column(name ="LABEL")
    private String label;
    @Column(name="VALEUR")
    private String valeur;
}