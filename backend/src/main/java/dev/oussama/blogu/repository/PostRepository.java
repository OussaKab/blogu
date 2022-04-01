package dev.oussama.blogu.repository;

import dev.oussama.blogu.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser_Credentials_Username(String username);
    List<Post> findAllByTitleOrDescriptionContaining(String search, String description);
}