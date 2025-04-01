package will.dev.avis_utilisateurs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh-token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valeur;
    private Instant creation;
    private Instant expiration;
    private Boolean expire;
}
