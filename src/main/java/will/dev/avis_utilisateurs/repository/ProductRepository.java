package will.dev.avis_utilisateurs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import will.dev.avis_utilisateurs.entities.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    //@Query("Select p From Product p Where p.name Like '%:param%' ")
    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%', :param, '%')")
    List<Product> findByName(String param);
}
