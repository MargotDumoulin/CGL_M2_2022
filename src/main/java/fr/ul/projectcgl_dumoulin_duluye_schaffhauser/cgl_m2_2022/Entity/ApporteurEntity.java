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
@Entity(name = "Apporteur")
@Table(name = "apporteur")
public class ApporteurEntity implements IEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    
    @Column(name = "IS_DELETED")
    private boolean isDeleted;

    @OneToOne
    @JoinColumn(name="parrain_id", referencedColumnName = "id")
    private ApporteurEntity parrain;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApporteurEntity that = (ApporteurEntity) o;
        return id != null && Objects.equals(id, that.getId());
    }
}
