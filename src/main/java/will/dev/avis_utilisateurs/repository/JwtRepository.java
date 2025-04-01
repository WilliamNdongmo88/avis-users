package will.dev.avis_utilisateurs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import will.dev.avis_utilisateurs.entities.Jwt;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Long> {
    Optional<Jwt> findByValeur(String valeur);
    Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, Boolean desactive, Boolean expire);

    @Query("SELECT j From Jwt j WHERE j.user.email =:email AND j.expire = :expire AND j.desactive = :desactive")
    Optional<Jwt> findUserValidToken(String email, Boolean desactive, Boolean expire);

    @Query("SELECT j From Jwt j WHERE j.user.email = :email")
    Stream<Jwt> findUser(String email);

    @Query("SELECT j FROM Jwt j WHERE j.expire=:expire AND j.desactive=:desactive")
    List<Jwt> deleteAllByExpireAndDesactiveJwt(Boolean expire, Boolean desactive);

    //-----------------------------Branch refresh token-------------------------------------------
    @Query("SELECT j FROM Jwt j WHERE j.refreshToken.valeur=:valeur")
    Optional<Jwt> findByRefreshToken(String valeur);


}
