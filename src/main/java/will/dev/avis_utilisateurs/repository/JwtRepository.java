package will.dev.avis_utilisateurs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import will.dev.avis_utilisateurs.entities.Jwt;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Long> {
    Optional<Jwt> findByValeur(String valeur);
    @Query("SELECT j From Jwt j WHERE j.user.email =:email AND j.expire = :expire AND j.desactive = :desactive")
    Optional<Jwt> findUserValidToken(String email, Boolean desactive, Boolean expire);

    @Query("SELECT j From Jwt j WHERE j.user.email = :email")
    Stream<Jwt> findUser(String email);

}
