package will.dev.avis_utilisateurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.Product;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    //Read
    public List<Product> search() {
        return (List<Product>) this.productRepository.findAll();
    }

    //Read
    public Optional<Product> lire(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return Optional.ofNullable(product);
    }

    //Update
    public ResponseEntity<?> modifier(Long id, Product product) {
        User userConnected = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product productDansBd = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (Objects.equals(productDansBd.getId(), product.getId()) && Objects.equals(productDansBd.getId(), id)){
            productDansBd.setName(product.getName());
            productDansBd.setPrice(product.getPrice());
            productDansBd.setDescription(product.getDescription());
            productDansBd.setAddedBy(userConnected);
            this.productRepository.save(productDansBd);
        }
        return ResponseEntity.ok(product.getName() + " has been updated Successfully ");
    }

    //Delete
    public ResponseEntity<?> delete(Long id) {
        Product productDansBd = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (productDansBd != null) {
            this.productRepository.deleteById(id);
        }
        return ResponseEntity.ok("Deleted successfully");
    }

    //Get by name
    public List<Product> searchByName(String param) {
        return this.productRepository.findByName(param);
    }
}
