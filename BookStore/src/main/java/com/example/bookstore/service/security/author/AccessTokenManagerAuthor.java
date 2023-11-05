
package com.example.bookstore.service.security.author;


import com.example.bookstore.models.entities.Author;
import com.example.bookstore.models.proporties.security.SecurityProperties;
import com.example.bookstore.service.base.TokenGenerator;
import com.example.bookstore.service.base.TokenReader;
import com.example.bookstore.service.getters.EmailGetter;
import com.example.bookstore.utils.PublicPrivateKeyUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.bookstore.constants.TokenConstants.EMAIL_KEY;


@Component
@Slf4j
@RequiredArgsConstructor
public class AccessTokenManagerAuthor implements TokenGenerator<Author>,
        TokenReader<Claims>, EmailGetter {
    private final SecurityProperties securityProperties;

    @Override
    public String generate(Author obj) {
        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY, obj.getEmail());
        claims.put("role", obj.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getAccessTokenValidityTime());

        return Jwts.builder()
                .setSubject(String.valueOf(obj.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();

    }

    @Override
    public Claims read(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }


            return claims;

    }

    @Override
    public String getEmail(String token) {

        if (read(token) != null) {
            return read(token).get(EMAIL_KEY, String.class);
        } else {
            return null;
        }
    }
}
