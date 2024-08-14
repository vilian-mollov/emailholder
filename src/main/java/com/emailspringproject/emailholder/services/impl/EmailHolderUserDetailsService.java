package com.emailspringproject.emailholder.services.impl;

import com.emailspringproject.emailholder.domain.entities.User;
import com.emailspringproject.emailholder.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public class EmailHolderUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public EmailHolderUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findFirstByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }


    private UserDetails map(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of())//TODO - add roles
                .build();

        return userDetails;
    }
}
