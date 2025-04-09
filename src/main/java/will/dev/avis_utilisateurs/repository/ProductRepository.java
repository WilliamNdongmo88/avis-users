package will.dev.avis_utilisateurs.repository;

import org.springframework.data.repository.CrudRepository;
import will.dev.avis_utilisateurs.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
