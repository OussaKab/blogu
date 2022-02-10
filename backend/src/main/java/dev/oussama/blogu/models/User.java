package dev.oussama.blogu.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User extends AbstractAuditingEntity  {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
                name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
                name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "email required")
    @Email(message = "Email invalide")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "password required")
    @Column(nullable = false)
    private String password;

    @Past(message = "Date of birth must be in the past")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
