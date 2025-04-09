package will.dev.avis_utilisateurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import will.dev.avis_utilisateurs.entities.Product;
import will.dev.avis_utilisateurs.services.ProductService;

@RestController
@RequestMapping(path = "product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('ADMIN_CREATE')")
    @PostMapping("create")
    public void create(@RequestBody Product product){
        this.productService.create(product);
    }
}
