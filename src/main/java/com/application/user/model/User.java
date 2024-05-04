package com.application.user.model;

import com.application.config.BaseEntity;
import com.application.content.volunteers.volunteer.model.Volunteer;
import com.application.security.token.Token;
import com.application.registration.model.ConfirmationEmail;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = {"confirmationEmail", "tokens"}, callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user")
@Table(schema = "user_data", name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_email", columnNames = { "email" }))
public class User extends BaseEntity implements UserDetails {

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

    @OneToOne(mappedBy = "user", optional = false, fetch = FetchType.LAZY)
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