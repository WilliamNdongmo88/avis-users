package will.dev.avis_utilisateurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import will.dev.avis_utilisateurs.entities.User;
import will.dev.avis_utilisateurs.services.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Get all users
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")//Permet de donner la permission uniquement aux admins
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'MANAGER_READ')")
    @GetMapping("all_users")
    public List<User> list(){
        return this.userService.listUsers();
    }

    //Get Account create today
    @GetMapping("account_create_today")
    public ResponseEntity<Map<String, Object>> getAccountCreateToDay(){
        return this.userService.getAccountCreateToDay();
    }
}
