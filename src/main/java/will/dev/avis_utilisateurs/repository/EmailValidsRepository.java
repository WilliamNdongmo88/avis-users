package will.dev.avis_utilisateurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import will.dev.avis_utilisateurs.entities.EmailValids;

import java.util.Optional;

public interface EmailValidsRepository extends JpaRepository<EmailValids, Long> {
    @Query("SELECT e FROM EmailValids e WHERE e.email=:email")
    Optional<EmailValids> findByEmail(String email);
}
