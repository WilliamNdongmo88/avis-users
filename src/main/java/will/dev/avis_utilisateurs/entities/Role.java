package will.dev.avis_utilisateurs.entities;

import jakarta.persistence.*;
import lombok.Data;
import will.dev.avis_utilisateurs.enums.TypeDeRole;

@Entity
@Table(name = "role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeDeRole lebelle;
}
