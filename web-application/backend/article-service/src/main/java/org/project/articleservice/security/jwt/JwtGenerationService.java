package org.project.articleservice.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Getter
@Service
public class JwtGenerationService {

    // TODO: Save in environment variable
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateJwt(Authentication authentication) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .issuer("dumm2")
                .issuedAt(new Date())
                .claim("username", authentication.getName())
                .claim("authorieis", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .expiration(new Date((new Date().getTime() + 1000 * 60 * 60)))
                .signWith(secretKey)
                .compact();

        return jwt;
    }


}
