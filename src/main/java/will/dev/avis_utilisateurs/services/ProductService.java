package will.dev.avis_utilisateurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.Product;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //Create
    public void create(Product product) {
        User userConnected = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        product.setAddedBy(userConnected);
        this.productRepository.save(product);
    }
}
