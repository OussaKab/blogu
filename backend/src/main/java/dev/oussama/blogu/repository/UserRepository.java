package dev.oussama.blogu.repository;

import dev.oussama.blogu.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCredentials_Username(String login);

    boolean existsByCredentials_Username(String username);

    boolean existsByEmail(String email);
}
