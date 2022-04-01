package dev.oussama.blogu.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post extends Publication {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @Lob
    private String description;

    private long views = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private boolean blocked;
}