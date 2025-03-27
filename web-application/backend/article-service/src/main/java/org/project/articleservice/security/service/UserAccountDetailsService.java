package org.project.articleservice.security.service;

import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.UserAccount;
import org.project.articleservice.repository.UserAccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found, username = " + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userAccount.getRole()));

        return User.withUsername(userAccount.getUsername())
                .password(userAccount.getPassword())
                .authorities(authorities)
                .build();
    }

}
