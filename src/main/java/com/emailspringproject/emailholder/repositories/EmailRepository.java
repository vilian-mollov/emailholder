package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
