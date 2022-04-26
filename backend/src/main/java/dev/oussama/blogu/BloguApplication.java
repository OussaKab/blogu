package dev.oussama.blogu;

import dev.oussama.blogu.services.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.Resource;

@SpringBootApplication
@EnableJpaAuditing
public class BloguApplication implements CommandLineRunner {

    @Resource
    private FileService fileService;

    public static void main(String[] args) {
        SpringApplication.run(BloguApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.fileService.setup();
    }
}
