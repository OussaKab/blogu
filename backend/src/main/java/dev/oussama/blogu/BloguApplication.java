package dev.oussama.blogu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BloguApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloguApplication.class, args);
    }

}
