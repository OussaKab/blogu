package dev.oussama.blogu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
@Profile("dev")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Setup data loader");
        if (alreadySetup) {
            return;
        }
        //TODO : logique de création des roles et privilèges

        alreadySetup = true;
    }
}
