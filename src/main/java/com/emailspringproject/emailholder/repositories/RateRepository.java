package com.emailspringproject.emailholder.repositories;


import com.emailspringproject.emailholder.domain.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
}
