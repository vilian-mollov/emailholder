package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
}
