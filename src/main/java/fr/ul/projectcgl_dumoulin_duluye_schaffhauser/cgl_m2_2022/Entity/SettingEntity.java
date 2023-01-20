package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Setting")
@Table(name = "parametres")
public class SettingEntity implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;
    @Column(name ="LABEL")
    private String label;
    @Column(name="VALEUR")
    private String valeur;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SettingEntity that = (SettingEntity) o;
        return id != null && Objects.equals(id, that.getId());
    }
}