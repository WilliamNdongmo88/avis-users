package will.dev.avis_utilisateurs.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.Validation;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;

    public void envoyer(Validation validation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-rely@will.dev");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Votre code d'activation");
        String text = String.format("Bonjour %s <br />Votre code d'activation est %s;<br />A bientôt",
                validation.getUser().getNom(),
                validation.getCode());
        message.setText(text);

        javaMailSender.send(message);
    }
}
