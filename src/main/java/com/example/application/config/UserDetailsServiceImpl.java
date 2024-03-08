package com.example.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.application.enums.Role;
import com.example.application.models.UserEntity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private com.example.application.repositories.UserRepository userRepository;

  @Override
  // actually loads by user metro card number
  public UserDetails loadUserByUsername(String metroCardNumber) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByMetroCardNumber(metroCardNumber)
        .orElseThrow(() -> new UsernameNotFoundException("User not found by metro card number " + metroCardNumber));

    return new User(user.getMetroCardNumber(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
    return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
  }

}
