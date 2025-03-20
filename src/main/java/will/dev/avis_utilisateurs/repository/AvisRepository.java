package will.dev.avis_utilisateurs.repository;

import org.springframework.data.repository.CrudRepository;
import will.dev.avis_utilisateurs.entities.Avis;

public interface AvisRepository extends CrudRepository<Avis, Long> {

}
