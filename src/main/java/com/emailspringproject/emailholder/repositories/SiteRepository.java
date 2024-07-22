package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    @Override
    Optional<Site> findById(Long aLong);

    List<Site> findAllByUser(User user);

    void deleteById(Long id);

}
