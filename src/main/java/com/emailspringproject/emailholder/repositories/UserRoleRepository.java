package com.emailspringproject.emailholder.repositories;

import com.emailspringproject.emailholder.domain.entities.UserRoleEntity;
import com.emailspringproject.emailholder.domain.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findAllByRoleIn(List<UserRoleEnum> roles);

}
