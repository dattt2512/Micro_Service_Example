package com.tdt.authservice.service.impl;

import com.tdt.authservice.entities.RoleEntity;
import com.tdt.authservice.entities.UserEntity;
import com.tdt.authservice.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserEntityRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepo.findByUsername(username);
        User newUser = null;

        if (user != null) {
            if (user.getDeletedDate() != null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            if (!user.isEnabled()) {
                throw new UsernameNotFoundException("User " + username + " is disabled");
            }
            List<RoleEntity> roles = user.getRoles();
            List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
            for (RoleEntity role: roles) {
                authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName()));
            }
            newUser = new User(user.getUsername(), user.getPassword(), authorities);
        }
        return newUser;
    }
}
