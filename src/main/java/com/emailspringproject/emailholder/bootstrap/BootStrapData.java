package com.emailspringproject.emailholder.bootstrap;


import com.emailspringproject.emailholder.domain.entities.*;
import com.emailspringproject.emailholder.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class BootStrapData implements CommandLineRunner {

    private final EmailRepository emailRepository;
    private  final SiteRepository siteRepository;

    private final UserRepository userRepository;

    public BootStrapData(EmailRepository emailRepository, SiteRepository siteRepository, UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Site facebook = new Site("https://www.facebook.com/", "Facebook");
        siteRepository.save(facebook);

        Site instagram = new Site("https://www.instagram.com/", "Instagram");
        siteRepository.save(instagram);

        Email email = new Email("vilian@gmail.com", "email description .....................");
        email.addSite(facebook);
        email.addSite(instagram);
        emailRepository.save(email);

        Email email2 = new Email("diablo@gmial.com", "email description .....................");
        email2.addSite(facebook);
        emailRepository.save(email2);

        Email email3 = new Email("alokard@abv.bg", "email description .....................");
        email3.addSite(facebook);
        emailRepository.save(email3);


        User user = new User();
        user.setUsername("alocard");
        userRepository.save(user);

        Optional<User> firstByUsername = userRepository.findFirstByUsername(user.getUsername());

        User user1 = firstByUsername.get();
        user1.addEmail(email);
        userRepository.save(user1);

        Optional<Email> e = emailRepository.findById(email.getId());
        Email email1 = e.get();
        email1.setUser(user1);
        emailRepository.save(email1);

        Optional<Email> e2 = emailRepository.findById(email2.getId());
        Email email4 = e2.get();
        email4.setUser(user1);
        emailRepository.save(email4);

        System.out.println("From BootStrap:");
        System.out.println("Number of emails: " + emailRepository.count());
        System.out.println("Number of sites: " + siteRepository.count());

    }


}
