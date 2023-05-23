package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
