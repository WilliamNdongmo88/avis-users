package will.dev.avis_utilisateurs.securite;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import will.dev.avis_utilisateurs.entities.Jwt;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.repository.JwtRepository;
import will.dev.avis_utilisateurs.services.UserService;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static will.dev.avis_utilisateurs.securite.KeyGeneratorUtil.generateEncryptionKey;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class JwtService {
    public static final String BEARER = "Bearer";
    //private final String ENCRYPTION_KEY = "9710e6844f0bb2a4aa13608d1f207a15fb9f35c602582ac6ba3525daceba966d";
    private final String ENCRYPTION_KEY = generateEncryptionKey(32);
    private final UserService userService;
    private final JwtRepository jwtRepository;

    // début branch déconnexion
    public Jwt tokenByValue(String token) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(token, false, false)
                .orElseThrow(() -> new RuntimeException("Token invalid ou inconnu"));
    }


    public void deconnexion() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();//Recupération du user connecté
        Jwt jwt = this.jwtRepository.findUserValidToken(user.getEmail(), false, false)
                .orElseThrow(()-> new RuntimeException("Token invalide"));
        jwt.setExpire(true);
        jwt.setDesactive(true);
        this.jwtRepository.save(jwt);
    }

    //@Scheduled(cron = "@daily")// L'execution se faira tous les jours
    @Scheduled(cron = "0 */1 * * * *")
    public void removeUselessJwt(){
        log.info("Suppresion des tokens a {} " + Instant.now());
        List<Jwt> tokens = this.jwtRepository.deleteAllByExpireAndDesactiveJwt(true, true);
        if (!tokens.isEmpty()) {
            jwtRepository.deleteAll(tokens);
            log.info("{} tokens supprimés.", tokens.size());
        } else {
            log.info("Aucun token à supprimer.");
        }
    }
    //Fin déconnexion

    //Début du traitement du token a générer
    public Map<String, String> generate(String username){
        User user = (User) this.userService.loadUserByUsername(username);
        this.disableTokens(user);//---------------------Désactivation de tous les tokens lié a l'utilisateur précedement creer
        Map<String, String> jwtMap = this.generateJwt(user);
        // début branch déconnexion
        Jwt jwt = Jwt
                .builder()
                .valeur(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .user(user)
                .build();
        this.jwtRepository.save(jwt);//Sauvegade la valeur du token dans la bd
        //Fin déconnexion

        return jwtMap;
    }

    private void disableTokens(User user){
        final List<Jwt> jwtList = this.jwtRepository.findUser(user.getEmail())
                .filter(jwt -> jwt.getUser() != null) // Évite les valeurs null
                .peek(jwt ->{
                    jwt.setDesactive(true);
                    jwt.setExpire(true);
                        }
                ).collect(Collectors.toList());
        this.jwtRepository.saveAll(jwtList);
    }


    private Map<String, String> generateJwt(User user) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + 30*60*1000;
        Map<String, Object> claims = Map.of(
                "nom", user.getNom(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getEmail()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .claims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey(){
        byte[] decoded = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoded);
    }
    //Fin du traitement

    //Début du traitement pour autoriser un user a éffectuer des actions dans l'apk grace au token
    public String extractUsername(String token) {
        return this.getClaims(token, Claims::getSubject);
    }

    public Boolean isTokenExpred(String token) {
        try {
            Date expirationDate = this.getClaims(token, Claims::getExpiration);
            return expirationDate.before(new Date());
        } catch (Exception e) {
            throw new RuntimeException("Le token a expiré");
        }
    }

    private <T> T getClaims(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Fin du traitement.
}
