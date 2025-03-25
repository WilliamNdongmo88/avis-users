package will.dev.avis_utilisateurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.Avis;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.repository.AvisRepository;

@Service
@RequiredArgsConstructor
public class AvisServices {
    private final AvisRepository avisRepository;

    //Create
    public void create(Avis avis){
        User userConnected = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//Recuperation du user connecté et authorisation a cet user de creer un avis grace a son token valide
        System.out.println("userConnected : "+ userConnected);
        avis.setUser(userConnected);
        this.avisRepository.save(avis);
    }
}
