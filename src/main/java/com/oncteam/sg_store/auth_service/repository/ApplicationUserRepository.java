package com.oncteam.sg_store.auth_service.repository;

import com.oncteam.sg_store.auth_service.domain.entity.ApplicationUser;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ApplicationUserRepository extends Repository<ApplicationUser, Long>{

    Optional<ApplicationUser> findByUsername(String username);

}
