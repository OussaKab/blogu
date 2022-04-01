package dev.oussama.blogu.controller;

import dev.oussama.blogu.models.Profile;
import dev.oussama.blogu.services.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/profiles")
public class ProfileController {


    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{username}")
    public Profile getProfile(@PathVariable String username){
        return profileService.getProfile(username);
    }
}
