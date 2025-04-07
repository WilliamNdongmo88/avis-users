package will.dev.avis_utilisateurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import will.dev.avis_utilisateurs.dto.AuthenticationDTO;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.securite.JwtService;
import will.dev.avis_utilisateurs.services.UserService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class CompteController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    //Register
    @PostMapping("inscription")
    public ResponseEntity<?>  inscription(@RequestBody User user){
        return this.userService.create(user);
    }

    //Activation
    @PostMapping("activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.userService.activation(activation);
    }

    //New Activation Code
    @PostMapping("new-activation-code")
    public void newActivationCode(@RequestBody Map<String, String> param){
        this.userService.modifierPassword(param);
    }

    //Modified Password
    @PostMapping("modified-password")
    public void modifierPassword(@RequestBody Map<String, String> param){
        this.userService.modifierPassword(param);
    }

    //New Password
    @PostMapping("new-password")
    public void newPassword(@RequestBody Map<String, String> param){
        this.userService.newPassword(param);
    }

    //Connexion
    @PostMapping("connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO){
       final Authentication authenticate = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
       );
       if (authenticate.isAuthenticated()){
           log.info("username: " + authenticationDTO.username());
           return this.jwtService.generate(authenticationDTO.username());//Retourne le token de connexion
       }
       return null;
    }

    //Refresh token
    @PostMapping("refresh-token")
    public Map<String, String> refreshToken(@RequestBody Map<String, String> refreshTokenRequest){
        return this.jwtService.refreshToken(refreshTokenRequest);
    }

    //deconnexion
    @PostMapping("deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }

}
