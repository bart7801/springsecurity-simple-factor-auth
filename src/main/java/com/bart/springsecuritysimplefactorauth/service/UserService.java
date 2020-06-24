package com.bart.springsecuritysimplefactorauth.service;

import com.bart.springsecuritysimplefactorauth.model.AppUser;
import com.bart.springsecuritysimplefactorauth.model.Token;
import com.bart.springsecuritysimplefactorauth.repo.AppUserRepo;
import com.bart.springsecuritysimplefactorauth.repo.TokenRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService {

    private final TokenRepo tokenRepo;
    private final MailService mailService;
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("ROLE_USER");
        appUserRepo.save(appUser);
        sendToken(appUser);
    }

    private void sendToken(AppUser appUser) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setAppUser(appUser);
        tokenRepo.save(token);
        String url = "http://localhost:8080/token?value=" + tokenValue;

        try {
            mailService.sendMail(appUser.getUsername(), "confirm here", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
