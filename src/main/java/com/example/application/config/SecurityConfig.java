package com.example.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Autowired
  private JwtAuthEntryPoint jwtAuthEntryPoint;

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsServiceImpl);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.csrf((csrf) -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorize) -> {
          authorize
              // authentications
              .requestMatchers("/auth/**").permitAll()
              // swagger3 api documentation
              .requestMatchers("/swagger-ui/**").permitAll()
              .requestMatchers("/v3/api-docs/**").permitAll()
              // stations
              .requestMatchers(HttpMethod.POST, "/stations").hasAuthority("ADMIN")
              .requestMatchers(HttpMethod.DELETE, "/stations/**").hasAuthority("ADMIN")
              .requestMatchers(HttpMethod.GET, "/stations/**").permitAll()
              // users
              .requestMatchers("/users/**").hasAuthority("ADMIN")
              // user profile
              .requestMatchers("/profile/**").hasAnyAuthority("ADMIN", "USER")
              // bookings
              .requestMatchers("/book").hasAnyAuthority("ADMIN", "USER")
              .requestMatchers("/book/**").hasAnyAuthority("ADMIN", "USER")
              // booking-history
              .requestMatchers("/booking-history/all").hasAuthority("ADMIN")
              .requestMatchers("/booking-history/**").hasAnyAuthority("ADMIN", "USER")
              // fare
              .requestMatchers("/fare").permitAll()
              //
              .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

    http.authenticationProvider(authenticationProvider());

    // Add JWT token filter
    http.addFilterBefore(
        jwtAuthFilter,
        UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
