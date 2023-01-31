package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.Email;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email,Long> {
}
