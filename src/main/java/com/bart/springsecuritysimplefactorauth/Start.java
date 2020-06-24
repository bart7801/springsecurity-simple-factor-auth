package com.bart.springsecuritysimplefactorauth;

import com.bart.springsecuritysimplefactorauth.model.AppUser;
import com.bart.springsecuritysimplefactorauth.repo.AppUserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Start {

    public Start(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {

        AppUser appUserJohn = new AppUser();
        appUserJohn.setUsername("John");
        appUserJohn.setPassword(passwordEncoder.encode("John123"));
        appUserJohn.setRole("ROLE_ADMIN");
        appUserJohn.setEnabled(true);

        AppUser appUserBart = new AppUser();
        appUserBart.setUsername("Bart");
        appUserBart.setPassword(passwordEncoder.encode("Bart123"));
        appUserBart.setRole("ROLE_USER");
        appUserBart.setEnabled(true);

        appUserRepo.save(appUserJohn);
        appUserRepo.save(appUserBart);
    }
}
