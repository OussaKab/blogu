package dev.oussama.blogu.config;

import dev.oussama.blogu.model.Role;
import dev.oussama.blogu.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    boolean alreadySetup = false;

    public SetupDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        log.info("Setup data loader");

        createRoleIfNotFound("ROLE_BLOGGER");
        createRoleIfNotFound("ROLE_MODERATOR");

        alreadySetup = true;
    }

    private void createRoleIfNotFound(String name) {
        if (roleRepository.findByName(name).isPresent())
            return;
        Role role = new Role();
        role.setName(name);
        ArtSoukUtils.setAuditingAttributes(role);
        roleRepository.save(role);
    }
}
