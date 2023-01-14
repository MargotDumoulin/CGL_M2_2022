package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Setting {
    private Long id;

    private String code;

    private String label;

    private String valeur;
}
