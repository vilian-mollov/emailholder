package com.emailspringproject.emailholder.bootstrap;


import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Site;
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








        System.out.println("From BootStrap:");
        System.out.println("Number of emails: " + emailRepository.count());
        System.out.println("Number of sites: " + siteRepository.count());

    }


}
