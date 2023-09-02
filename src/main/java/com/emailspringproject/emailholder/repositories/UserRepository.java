package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByUsername(String username);

    Optional<User> findFirstByMainEmail(String mainEmail);

}
