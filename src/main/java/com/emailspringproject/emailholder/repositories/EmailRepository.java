package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Email;
import com.emailspringproject.emailholder.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Email findFirstById(Long id);

    List<Email> findAllByUser(User user);

    Optional<Email> findFirstByEmailAddress(String emailAddress);
}
