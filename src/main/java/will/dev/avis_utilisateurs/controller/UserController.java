package will.dev.avis_utilisateurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.services.UserService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    //Register
    @PostMapping("inscription")
    public void inscription(@RequestBody User user){
        this.userService.create(user);
    }

    //Activation
    @PostMapping("activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.userService.activation(activation);
    }
}
