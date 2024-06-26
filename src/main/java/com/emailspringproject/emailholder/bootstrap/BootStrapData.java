package com.emailspringproject.emailholder.bootstrap;


import com.emailspringproject.emailholder.domain.entities.*;
import com.emailspringproject.emailholder.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class BootStrapData implements CommandLineRunner {

    private final EmailRepository emailRepository;
    private  final SiteRepository siteRepository;

    private final UserRepository userRepository;
    private PasswordEncoder encoder;

    @Value("${PASSWORD}")
    private String pass;

    public BootStrapData(EmailRepository emailRepository, SiteRepository siteRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Site facebook = new Site("https://www.facebook.com/", "Facebook");
        siteRepository.save(facebook);

        Site instagram = new Site("https://www.instagram.com/", "Instagram");
        siteRepository.save(instagram);

        Site linkedIn = new Site("https://www.linkedin.com/", "LinkedIn");
        siteRepository.save(linkedIn);

        Email email = new Email("immortals@gmail.com", "email description .....................");
        email.addSite(facebook);
        email.addSite(instagram);
        email.addSite(linkedIn);
        for (int i = 1; i <= 100; i++) {
            Site test100 = new Site("https://www.test"+i+".com/", "test" + i);
            siteRepository.save(test100);
            email.addSite(test100);
        }
        emailRepository.save(email);

        Email email2 = new Email("diablo@gmial.com", "email description .....................");
        email2.addSite(facebook);
        emailRepository.save(email2);

        Email email3 = new Email("alokard@abv.bg", "email description .....................");
        email3.addSite(facebook);
        emailRepository.save(email3);


        User user = new User();
        user.setUsername("test");
        user.setPassword(encoder.encode(pass));
        userRepository.save(user);

        Optional<User> firstByUsername = userRepository.findFirstByUsername(user.getUsername());

        User user1 = firstByUsername.get();
        user1.addEmail(email);
        user1.addEmail(email2);
        user1.addEmail(email3);
        userRepository.save(user1);



        System.out.println("From BootStrap:");
        System.out.println("Number of emails: " + emailRepository.count());
        System.out.println("Number of sites: " + siteRepository.count());
        System.out.println("Number of users: " + userRepository.count());

    }


}
