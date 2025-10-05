package org.example.gfgblr9.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.gfgblr9.filters.JwtRequestFilter;
import org.example.gfgblr9.repositories.LibraryUserRepository;
import org.example.gfgblr9.services.CustomUserDetailsService;
import org.example.gfgblr9.services.JwtUserDetailsService;
import org.example.gfgblr9.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    private LibraryUserRepository libraryUserRepository;

    /*@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // PasswordEncoder is used to encode passwords before they are stored
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Create an admin user
        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("adminpass"))
                .roles("ADMIN", "CUSTOMER","LIBRARIAN") // Can have multiple roles
                .build();

        //Create a librarian user
        // Create an admin user
        UserDetails librarian = User.withUsername("librarian")
                .password(encoder.encode("librarianpass"))
                .roles("CUSTOMER","LIBRARIAN") // Can have multiple roles
                .build();

        // Create a standard user
        UserDetails user = User.withUsername("user")
                .password(encoder.encode("userpass"))
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(admin, user, librarian);
    }*/


    //@Autowired
    //private UserRepository userRepository;
    // Basic auth
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // THIS IS THE FIX: Allow public access to the login page
                        .requestMatchers("/register","/login").permitAll()
                        // Also permit access to any CSS or JS files needed by the login page
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }*/
    //Form based login
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // THIS IS THE FIX: Allow public access to the login page
                        .requestMatchers("/login").permitAll()
                        // Also permit access to any CSS or JS files needed by the login page
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/v1/addAsset").hasRole("ADMIN")
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .userDetailsService(new CustomUserDetailsService(libraryUserRepository))
                .httpBasic(withDefaults()

                );
        //UsernamePasswordAuthenticationFilter
        return http.build();
    }*/
    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
        successHandler.setTargetUrlParameter("http://localhost:8081/session-expired");
        //successHandler.setDefaultTargetUrl("http://localhost:3000/session-expired");
        SecurityContextHolder.clearContext();// Replace with your redirect URL

        return successHandler;
    }
    //oath authentication
   /* public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/error", "/webjars/**", "/session-expired", "/login/oauth2/code/github").permitAll() // Public pages
                        .anyRequest().authenticated() // All other pages require authentication
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                )
                .oauth2Login();
        return http.build();
    }*/
   // jwt authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                //.userDetailsService(new JwtUserDetailsService("LIBRARIAN"))
                .authorizeHttpRequests(auth -> auth
                        // THIS IS THE FIX: Allow public access to the login page
                        .requestMatchers("/login").permitAll()
                        // Also permit access to any CSS or JS files needed by the login page
                        .requestMatchers("/css/**", "/js/**","/error/**","/","/weather").permitAll()
                        .requestMatchers("/v1/addAsset").hasRole("ADMIN")
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .invalidSessionUrl("/invalidSession")
                                .invalidSessionStrategy(invalidSessionStrategy())
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                )
                .addFilterBefore(new JwtRequestFilter(new JwtUtil()), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                // .addFilterBefore(new CustomSessionValidationFilter(), SessionManagementFilter.class)

        return http.build();
    }
    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new SimpleRedirectInvalidSessionStrategy("/session-expired");
    }

   /* @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt is a strong, widely-used hashing algorithm
        return new BCryptPasswordEncoder();
    }*/

    //Database authentication





    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->

                        authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/login", "get", true)).permitAll()
                                .anyRequest().authenticated()  // All other requests should be authenticated
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")  // Configure the custom login page
                        .defaultSuccessUrl("/home", true)  // Redirect to home after successful login
                        .permitAll()  // Allow all to access the login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")  // On logout, redirect to login with query parameter
                        .permitAll()  // Allow all to trigger logout
                );
        return http.build();
    }*/

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    /*@Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService(libraryUserRepository);
    }*/
   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic(withDefaults())
                .userDetailsService(userDetailsService())
                .authorizeRequests()
                .requestMatchers("/v1/users/**")
                .hasRole("LIBRARIAN")
                .requestMatchers("/login*")
                .permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }*/

    //jwt
    /*
     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
            .authorizeHttpRequests(auth -> auth
                // Allow access to the authentication endpoint
                .requestMatchers("/api/auth/**").permitAll()
                // All other requests must be authenticated
                .anyRequest().authenticated()
            )
            // Tell Spring Security not to create or use sessions
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            // Add our custom JWT filter before the standard username/password filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
     */


    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .csrf()
                 .disable()
                 .authorizeRequests(authorize -> authorize
                         .requestMatchers( "/", "/error", "/webjars/**", "/session-expired","/login/oauth2/code/github").permitAll() // Public pages
                         .anyRequest().authenticated() // All other pages require authentication
                 )
                 .logout(logout -> logout
                         .logoutUrl("/logout")
                         .logoutSuccessHandler(oidcLogoutSuccessHandler())
                         .invalidateHttpSession(true)
                         .deleteCookies("JSESSIONID")
                         .clearAuthentication(true)
                 )
                 .oauth2Login();


         return http.build();
    }*/

    /*private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
        successHandler.setTargetUrlParameter("http://localhost:3000/session-expired");
        //successHandler.setDefaultTargetUrl("http://localhost:3000/session-expired");
        SecurityContextHolder.clearContext();// Replace with your redirect URL

        return successHandler;
    }*/

    //
    // eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJzdWIiOiJzYWhhc3VtYWx5YSIsIklzc3VlciI6Iklzc3VlciIsImV4cCI6MTc0OTk3OTM3MCwiaWF0IjoxNzQ5ODkyOTcwfQ.YE9Ra8Vz3CteBitnb9ovICYnShvdDZzX8-voMg6Aaic

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic(withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/invalidSession")
                        .invalidSessionStrategy(invalidSessionStrategy())
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        )

                //.userDetailsService(userDetailsService())
                .addFilterAfter(new JwtRequestFilter(new JwtUserDetailsService(), new JwtUtil()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomSessionValidationFilter(), SessionManagementFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        return http.build();
    }*/

   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .httpBasic(withDefaults())
                /*.sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .invalidSessionUrl("/invalidSession")
                                .invalidSessionStrategy(invalidSessionStrategy())
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                )*/
                /*.logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/session-expired")
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "token")
                        .clearAuthentication(true)
                )

                //.userDetailsService(userDetailsService())
                //.addFilterAfter(new JwtRequestFilter(new JwtUserDetailsService(), new JwtUtil()), UsernamePasswordAuthenticationFilter.class)
                // .addFilterBefore(new CustomSessionValidationFilter(), SessionManagementFilter.class)
                .authorizeRequests()
                .requestMatchers("/v1/users/login", "/v1/users/register", "/session-expired", "/invalidSession", "/v1/weather", "/parse-csv")
                .permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }*/


   /* @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new SimpleRedirectInvalidSessionStrategy("/session-expired");
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    /*@Bean
    public SecurityContextHolderStrategy securityContextHolderStrategy() {
        return new SecurityContextHolderStrategy() {
            @Override
            public void clearContext() {

            }

            @Override
            public SecurityContext getContext() {
                return null;
            }

            @Override
            public void setContext(SecurityContext context) {

            }

            @Override
            public SecurityContext createEmptyContext() {
                return null;
            }
        }
    }*/

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic(withDefaults())
                .authorizeRequests()
                .requestMatchers("/login", "/home").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }*/


}