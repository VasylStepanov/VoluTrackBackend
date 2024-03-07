package com.application.user.model;

import com.application.security.token.Token;
import com.application.registration.module.ConfirmationEmail;
import com.application.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"confirmationEmail", "tokens"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user")
@Table(schema = "user_data", name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_email", columnNames = { "email" }))
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "firstname", length = 64, nullable = false)
    String firstName;

    @Column(name = "lastname", length = 64, nullable = false)
    String lastName;

    @Column(length = 80, nullable = false)
    String email;

    @Column(length = 128, nullable = false)
    String password;

    @Column(name = "is_locked", nullable = false)
    boolean locked;

    @Column(name = "is_enabled", nullable = false)
    boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @OneToOne(mappedBy = "user", optional = false, fetch = FetchType.LAZY)
    ConfirmationEmail confirmationEmail;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Token> tokens;

    @OneToOne(mappedBy = "user")
    Volunteer volunteer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}