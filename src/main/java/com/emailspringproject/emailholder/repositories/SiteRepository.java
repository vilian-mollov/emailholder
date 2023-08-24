package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
