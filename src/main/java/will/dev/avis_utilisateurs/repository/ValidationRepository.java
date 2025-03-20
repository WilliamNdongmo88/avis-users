package will.dev.avis_utilisateurs.repository;

import org.springframework.data.repository.CrudRepository;
import will.dev.avis_utilisateurs.entities.Validation;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<Validation, Long> {

    Optional<Validation> findByCode(String code);
}
