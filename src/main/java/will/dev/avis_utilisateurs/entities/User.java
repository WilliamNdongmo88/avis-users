package will.dev.avis_utilisateurs.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String email;
    @Column (name="mot_de_passe")
    private String mdp;
    @Column(nullable = false)
    //private String role;
    private Boolean actif = false;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Role role;

    // Nouveau champ pour stocker uniquement le jour
    private LocalDate createDay;

    // Méthode pour définir automatiquement le jour de la creation du compte
    @PrePersist
    public void prePersist() {
        this.createDay = LocalDate.now(ZoneId.systemDefault()); // Stocke la date actuelle
    }

    //Start-------------------------------------------Branch Role&Permission
    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.getLebelle()));
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getLebelle().getAuthorities();
    }
    //End-------------------------------------------Branch Role&Permission

    @Override
    public String getPassword() {
        return this.mdp;
    }

    @Override
    public String getUsername() {
        return this.nom;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
