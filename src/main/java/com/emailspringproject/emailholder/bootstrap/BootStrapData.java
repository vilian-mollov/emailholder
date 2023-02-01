package com.emailspringproject.emailholder.bootstrap;


import com.emailspringproject.emailholder.domain.Email;
import com.emailspringproject.emailholder.domain.Site;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BootStrapData implements CommandLineRunner {

    private final EmailRepository emailRepository;
    private  final SiteRepository siteRepository;

    public BootStrapData(EmailRepository emailRepository, SiteRepository siteRepository) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Email email = new Email("vilian@gmail.com");
        emailRepository.save(email);


        Site facebook = new Site("https://www.facebook.com/", "Facebook");
        siteRepository.save(facebook);
        email.getSites().add(facebook);
        facebook.setEmail(email);

        Site instagram = new Site("https://www.instagram.com/", "Instagram");
        siteRepository.save(instagram);
        email.getSites().add(instagram);
        instagram.setEmail(email);



        System.out.println("From BootStrap:");
        System.out.println("Number of emails: " + emailRepository.count());
        System.out.println("Number of sites: " + siteRepository.count());

    }


}
