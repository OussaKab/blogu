package dev.oussama.blogu.repository;

import dev.oussama.blogu.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser_Credentials_Username(String username);

    List<Post> findAllByTitleOrDescriptionContaining(String search, String description);

    List<Post> findAllByModeration_Moderator_Credentials_UsernameAndModeration_BlockedTrue(String username);


    List<Post> findAllByModeration_BlockedFalse();
}