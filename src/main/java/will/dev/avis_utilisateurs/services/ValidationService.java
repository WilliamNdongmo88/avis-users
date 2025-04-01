package will.dev.avis_utilisateurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.entities.Validation;
import will.dev.avis_utilisateurs.repository.ValidationRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    public void enregistrer(User user){
        Validation validation = new Validation();
        Optional<Validation> existingValidation = validationRepository.findByUser(user);
        if (existingValidation.isPresent()) {
            this.updateVlidation(existingValidation.get());
        }else {
            validation.setUser(user);

            Instant creation = Instant.now();
            validation.setCreation(creation);
            Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
            validation.setExpiration(expiration);

            Random random = new Random();
            int randomInteger = random.nextInt(999999);
            String code = String.format("%06d", randomInteger);
            validation.setCode(code);

            this.validationRepository.save(validation);
            this.notificationService.envoyer(validation);
        }
    }

    // Update
    private void updateVlidation(Validation validation) {
        //User userConnected = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Validation validationDansBd = validationRepository.findById(validation.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + validation.getId()));

        validationDansBd.setUser(validation.getUser());
        Instant creation = Instant.now();
        validationDansBd.setCreation(creation);
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validationDansBd.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        validationDansBd.setCode(code);

        this.validationRepository.save(validationDansBd);
        this.notificationService.envoyer(validationDansBd);
    }

    private void updateValidation(User user) {
        Optional<Validation> existingValidation = validationRepository.findByUser(user);
        Instant creation = Instant.now();
        existingValidation.get().setCreation(creation);
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        existingValidation.get().setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);
        existingValidation.get().setCode(code);

        existingValidation.get().setUser(user);
        this.validationRepository.save(existingValidation.get());
        this.notificationService.envoyer(existingValidation.get());
    }

    public Validation lireCode(String code){
        return this.validationRepository.findByCode(code).orElseThrow(()-> new RuntimeException("Votre code est invalide"));
    }
}
