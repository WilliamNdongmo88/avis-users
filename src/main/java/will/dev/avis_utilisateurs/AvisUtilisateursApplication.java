package will.dev.avis_utilisateurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling// Permet de programmer les taches
@SpringBootApplication
public class AvisUtilisateursApplication {
	public static void main(String[] args) {
		SpringApplication.run(AvisUtilisateursApplication.class, args);
	}
}
