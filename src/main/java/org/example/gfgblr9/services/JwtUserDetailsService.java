package org.example.gfgblr9.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class JwtUserDetailsService implements UserDetailsService {
    private String roles;
    public JwtUserDetailsService(String roles) {
        this.roles = roles;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username, "", List.of(new SimpleGrantedAuthority(roles)) );
    }
}
