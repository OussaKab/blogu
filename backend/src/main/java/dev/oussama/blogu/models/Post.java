package dev.oussama.blogu.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
public class Post extends Publication {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content cdn required")
    private String contentUri;

    private String thumbnailUri;

    private long views;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private ContentType contentType = ContentType.TEXT;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> tags;
}