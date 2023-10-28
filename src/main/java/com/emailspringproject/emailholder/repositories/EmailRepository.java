package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    Email findFirstById(Long id);
}
