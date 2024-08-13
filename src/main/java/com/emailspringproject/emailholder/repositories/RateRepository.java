package com.emailspringproject.emailholder.repositories;


import com.emailspringproject.emailholder.domain.entities.Rate;
import com.emailspringproject.emailholder.domain.entities.Site;
import com.emailspringproject.emailholder.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Optional<Rate> findFirstByUserAndSite(User user, Site site);
}
