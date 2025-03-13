package org.project.articleservice.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    // TODO: Save in environment variable
    private static final String JWT_SECRET = "df550dedf2f0ef43f4b0c57f7762f64f60e8f9bc3b36764cb08b15b7345d47fb0791f62b7ed19eac91e8ce93088abad72f089921eb4666d83cf87b15256a6e8d2ce2257c4573a405d293018a5e88f3acb6476c52b506066c50064543f6813d21213473eaf5dc1ebea09f445f194ecb352effc92c2b363cbfadcb429ae91dbf7cf5b79caaffe15548431dd12a1771d74439736a11153b4f3e64a87338983aebcaa7b6a8ddc2d18ba2a269d58aa00ee13703af87f67e306225cb3731713201f33ad62612714757c7e5188d276f80581b64d560da9186b823534fb4cc8e206b1bb8c8c890648dad629e96490c5c8d7a18409f5c80dbdb3a8dddb4008db346c8756a";

    public String generateJwt(Authentication authentication) {
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

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
