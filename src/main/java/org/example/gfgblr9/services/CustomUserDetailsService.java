package org.example.gfgblr9.services;

import org.example.gfgblr9.models.LibraryUser;
import org.example.gfgblr9.repositories.LibraryUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.TreeSet;

public class CustomUserDetailsService implements UserDetailsService {
    private final LibraryUserRepository libraryUserRepository;

    public CustomUserDetailsService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibraryUser user = libraryUserRepository.findByUsername(username);
        TreeSet<GrantedAuthority> grantedAuthorities = new TreeSet<>();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else{
            return new org.springframework.security.core.userdetails.User(user.getUsername(), "{noop}"+user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name())));
        }
    }
}
