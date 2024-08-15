package com.emailspringproject.emailholder.bootstrap;


import com.emailspringproject.emailholder.domain.entities.Comment;
import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.Rate;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.domain.entities.UserRoleEntity;
import com.emailspringproject.emailholder.domain.enums.UserRoleEnum;
import com.emailspringproject.emailholder.repositories.CommentRepository;
import com.emailspringproject.emailholder.repositories.EmailRepository;
import com.emailspringproject.emailholder.repositories.RateRepository;
import com.emailspringproject.emailholder.repositories.SiteRepository;
import com.emailspringproject.emailholder.repositories.UserRepository;
import com.emailspringproject.emailholder.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Component
public class BootStrapData implements CommandLineRunner {

    private final EmailRepository emailRepository;
    private final SiteRepository siteRepository;
    private final UserRepository userRepository;
    private final RateRepository rateRepository;
    private final CommentRepository commentRepository;

    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder encoder;

    @Value("${PASSWORD}")
    private String pass;

    public BootStrapData(EmailRepository emailRepository,
                         SiteRepository siteRepository,
                         UserRepository userRepository,
                         RateRepository rateRepository,
                         CommentRepository commentRepository,
                         UserRoleRepository userRoleRepository, PasswordEncoder encoder) {
        this.emailRepository = emailRepository;
        this.siteRepository = siteRepository;
        this.userRepository = userRepository;
        this.rateRepository = rateRepository;
        this.commentRepository = commentRepository;
        this.userRoleRepository = userRoleRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

//      Roles ---------------------------------------------------------------------------------------------------------------------------
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(UserRoleEnum.USER);
        userRoleRepository.save(userRole);

        UserRoleEntity adminRole = new UserRoleEntity();
        adminRole.setRole(UserRoleEnum.ADMIN);
        userRoleRepository.save(adminRole);

//      Users ---------------------------------------------------------------------------------------------------------------------------

        User user = new User();
        user.setUsername("test");
        user.setMainEmail("test28@testing.com");
        user.setPassword(encoder.encode(pass));
        user.setRoles(List.of(userRole));
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("lethimcook_24");
        user2.setMainEmail("lethimcooky@testing.com");
        user2.setPassword(encoder.encode(pass));
        user2.setRoles(List.of(adminRole));
        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("daniel2489");
        user3.setMainEmail("dan@testing.com");
        user3.setPassword(encoder.encode(pass));
        user3.setRoles(List.of(userRole));
        userRepository.save(user3);

        User user4 = new User();
        user4.setUsername("Richard_009");
        user4.setMainEmail("lionhearth@testing.com");
        user4.setPassword(encoder.encode(pass));
        user4.setRoles(List.of(userRole, adminRole));
        userRepository.save(user4);

//      Sites --------------------------------------------------------------------------------------------------------------------------

        Site facebook = new Site("https://www.facebook.com", "Facebook", user, new HashSet<>(), new HashSet<>());
        facebook.setSafety(true);
        siteRepository.save(facebook);
        addCommentToSite(facebook, user4);
        addCommentToSite(facebook, user3);
        addCommentToSite(facebook, user2);
        rateSite(facebook, user);

        Site notSafetySite = new Site("http://www.strangethings.com", "StrangeThings", user, new HashSet<>(), new HashSet<>());
        notSafetySite.setSafety(false);
        siteRepository.save(notSafetySite);
        addCommentToSite(notSafetySite, user2);
        rateSite(notSafetySite, user);

        Site instagram = new Site("https://www.instagram.com", "Instagram", user, new HashSet<>(), new HashSet<>());
        instagram.setSafety(true);
        siteRepository.save(instagram);
        addCommentToSite(instagram, user4);
        addCommentToSite(instagram, user3);
        addCommentToSite(instagram, user2);
        rateSite(instagram, user);

        Site linkedIn = new Site("https://www.linkedin.com", "LinkedIn", user, new HashSet<>(), new HashSet<>());
        linkedIn.setSafety(true);
        siteRepository.save(linkedIn);
        addCommentToSite(linkedIn, user4);
        addCommentToSite(linkedIn, user3);
        addCommentToSite(linkedIn, user2);
        rateSite(linkedIn, user);

        Email email = new Email("immortals@gmail.com", "email description .....................");
        email.addSite(facebook);
        email.addSite(instagram);
        email.addSite(linkedIn);
        for (int i = 1; i <= 3; i++) {
            Site testSite = new Site("https://www.test" + i + ".com", "test" + i, user, new HashSet<>(), new HashSet<>());
            testSite.setSafety(true);
            siteRepository.save(testSite);
            addCommentToSite(testSite, user4);
            rateSite(testSite, user);
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

    private void rateSite(Site site, User user) {
//      Rate
        Random random = new Random();
        Rate rate = new Rate(random.nextInt(1, 6), user, site);
//      Save the at last Rate
        rateRepository.save(rate);

        user.getRates().add(rate);
        userRepository.save(user);

        site.getRates().add(rate);
        siteRepository.save(site);
    }

    private void addCommentToSite(Site site, User user) {
        Random random = new Random();
        List<String> comments = new ArrayList<>();
        comments.add("Amazing site. I am using it every day!");
        comments.add("Need improvements");
        comments.add("The new feature is awesome! Thanks guys! XD");
        comments.add("No a big fan, but it is very fast.");
        comments.add("Frontend needs refactoring, it is really not user friendly!");

        int index = random.nextInt(0, 4);
        String randomComment = comments.get(index);

        Comment comment = new Comment(randomComment, user, site, LocalDateTime.now());
        commentRepository.save(comment);

        user.getComments().add(comment);
        userRepository.save(user);

        site.getComments().add(comment);
        siteRepository.save(site);
    }


}
