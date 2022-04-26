package dev.oussama.blogu.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.oussama.blogu.web.Credentials;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class User extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Embedded
    @NotNull
    private Credentials credentials;

    @NotBlank(message = "email required")
    @Email(message = "Email invalide")
    @Column(unique = true, nullable = false)
    private String email;

    @Transient
    public String getUsername() {
        return this.credentials != null ? this.credentials.getUsername() : null;
    }

    @Transient
    public String getPassword() {
        return this.credentials != null ? this.credentials.getPassword() : null;
    }
}
