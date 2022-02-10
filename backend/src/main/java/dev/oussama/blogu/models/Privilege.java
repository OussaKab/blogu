package dev.oussama.blogu.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Privilege extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;
}
