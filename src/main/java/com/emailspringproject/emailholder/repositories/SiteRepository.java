package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    @Override
    Optional<Site> findById(Long aLong);

    void deleteById(Long id);

}
