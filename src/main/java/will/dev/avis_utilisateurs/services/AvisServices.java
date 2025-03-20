package will.dev.avis_utilisateurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.Avis;
import will.dev.avis_utilisateurs.repository.AvisRepository;

@Service
@RequiredArgsConstructor
public class AvisServices {
    private final AvisRepository avisRepository;

    //Create
    public void create(Avis avis){
        this.avisRepository.save(avis);
    }
}
