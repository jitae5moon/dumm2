package org.project.articleservice.security.service;

import lombok.RequiredArgsConstructor;
import org.project.articleservice.domain.Member;
import org.project.articleservice.repository.MemberRepository;
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
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found, username = " + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole()));

        return User.withUsername(member.getUsername())
                .password(member.getPassword())
                .authorities(authorities)
                .build();
    }

}
