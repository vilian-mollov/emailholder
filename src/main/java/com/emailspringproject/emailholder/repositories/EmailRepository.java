package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Email findFirstById(Long id);

    List<Email> findAllByUser(User user);


//    void deleteEmailById(Long id);

    void deleteById(Long id);
//    @Override
//    void deleteById(Long id);
//
//    void deleteByDescription(String description);
//
//    void deleteEmailByEmailAddress(String emailAddress);
}
