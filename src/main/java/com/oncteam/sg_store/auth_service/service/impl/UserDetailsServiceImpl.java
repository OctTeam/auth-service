package com.oncteam.sg_store.auth_service.service.impl;

import com.oncteam.sg_store.auth_service.domain.entity.ApplicationUser;
import com.oncteam.sg_store.auth_service.repository.ApplicationUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> userData = applicationUserRepository.findByUsername(username);
        if (!userData.isPresent()) throw new UsernameNotFoundException(username);
        ApplicationUser data = userData.get();
        return new User(data.getUsername(), data.getPassword(), Collections.emptyList());
    }
}
