package xyz.mariosm.status.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.mariosm.status.http.UserPayload;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Entity
@Table(name = "users")
@Builder @AllArgsConstructor @RequiredArgsConstructor
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @NonNull @Setter @Column(unique = true)
    private String username;

    @NonNull @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User(UserPayload payload) {
        this.username = payload.username();
        this.password = payload.password();
    }

    public User() {}

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override @NotNull
    public String getPassword() {
        return password;
    }

    @Override @NotNull
    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return String.format("User{%s,%s}", username, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password,
                                                                                                        user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
