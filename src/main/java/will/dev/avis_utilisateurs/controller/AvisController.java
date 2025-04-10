package will.dev.avis_utilisateurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import will.dev.avis_utilisateurs.entities.Avis;
import will.dev.avis_utilisateurs.entities.Jwt;
import will.dev.avis_utilisateurs.services.AvisServices;

import java.util.List;

@RestController
@RequestMapping("/avis")
@RequiredArgsConstructor
public class AvisController {
    private final AvisServices avisServices;

    //POST
    @PreAuthorize("hasAnyAuthority('USER_CREATE_AVIS')")
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Avis avis){
        this.avisServices.create(avis);
    }

    //GET
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MANAGER_READ')")
    @GetMapping("all_avis")
    public List<Avis> list(){
        return this.avisServices.listAvis();
    }
}
