package will.dev.avis_utilisateurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import will.dev.avis_utilisateurs.entities.Avis;
import will.dev.avis_utilisateurs.services.AvisServices;

@RestController
@RequestMapping("avis")
@RequiredArgsConstructor
public class AvisController {
    private final AvisServices avisServices;

    //POST
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Avis avis){
        this.avisServices.create(avis);
    }
}
