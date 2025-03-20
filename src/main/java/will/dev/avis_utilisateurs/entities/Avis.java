package will.dev.avis_utilisateurs.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "avis")
@Data
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String status;
}
