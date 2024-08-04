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

        User user = new User();
        user.setUsername("test");
        user.setPassword(encoder.encode(pass));
        userRepository.save(user);

        Site facebook = new Site("https://www.facebook.com/", "Facebook", user, new ArrayList<>(), new ArrayList<>());
        facebook.setSafety(true);
        siteRepository.save(facebook);

        Site notSafetySite = new Site("http://www.strangethings.com/", "StrangeThings", user, new ArrayList<>(), new ArrayList<>());
        notSafetySite.setSafety(false);
        siteRepository.save(notSafetySite);

        Site instagram = new Site("https://www.instagram.com/", "Instagram", user, new ArrayList<>(), new ArrayList<>());
        instagram.setSafety(true);
        siteRepository.save(instagram);

        Site linkedIn = new Site("https://www.linkedin.com/", "LinkedIn", user, new ArrayList<>(), new ArrayList<>());
        linkedIn.setSafety(true);
        siteRepository.save(linkedIn);

        Email email = new Email("immortals@gmail.com", "email description .....................");
        email.addSite(facebook);
        email.addSite(instagram);
        email.addSite(linkedIn);
        for (int i = 1; i <= 3; i++) {
            Site testSite = new Site("https://www.test"+i+".com/", "test" + i, user, new ArrayList<>(), new ArrayList<>());
            testSite.setSafety(true);
            siteRepository.save(testSite);
            email.addSite(testSite);
        }
        emailRepository.save(email);

        Email email2 = new Email("diablo@gmial.com", "email description .....................");
        email2.addSite(facebook);
        emailRepository.save(email2);

        Email email3 = new Email("alokard@abv.bg", "email description .....................");
        email3.addSite(facebook);
        emailRepository.save(email3);




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
