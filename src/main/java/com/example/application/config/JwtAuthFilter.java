package com.example.application.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.application.enums.Role;
import com.example.application.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getServletPath().contains("auth") ||
        request.getServletPath().contains("swagger-ui") ||
        request.getServletPath().contains("api-docs")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get authorization header and validate
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header.isEmpty() || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get jwt token and validate
    final String token = header.split(" ")[1].trim();
    if (!jwtUtil.validateToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get user identity and set it on the spring security context
    String metroCardNumber = jwtUtil.getUsernameFromJWT(token);

    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(metroCardNumber);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
        null,
        userDetails.getAuthorities());

    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    filterChain.doFilter(request, response);
  }
}
