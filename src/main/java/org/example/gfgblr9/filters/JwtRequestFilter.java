package org.example.gfgblr9.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gfgblr9.services.JwtUserDetailsService;
import org.example.gfgblr9.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtRequestFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;


    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authorizationToken = null;
        if(request.getCookies() != null && request.getCookies().length > 0) {
            Optional<Cookie> token = Arrays.stream(request.getCookies()).filter(cookie ->cookie.getName().equals("token")).findFirst();
            if(token.isPresent()) {
                authorizationToken = token.get().getValue();
            }
        }
        String username = null;
        String role = null;

        if (authorizationToken != null) {
            username = jwtUtil.extractUsername(authorizationToken);
            role = jwtUtil.extractRole(authorizationToken);
        }

        //OAuth2AuthorizationRequestResolver
        //OAuth2AuthorizedClient

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            JwtUserDetailsService userDetailsService = new JwtUserDetailsService(role);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(authorizationToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


}