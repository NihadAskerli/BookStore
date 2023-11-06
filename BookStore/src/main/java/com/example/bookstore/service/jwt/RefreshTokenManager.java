
package com.example.bookstore.service.jwt;

import com.example.bookstore.exception.BaseException;
import com.example.bookstore.models.dto.jwt.JwtDto;
import com.example.bookstore.models.proporties.security.SecurityProperties;
import com.example.bookstore.service.base.JwtBase;
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
import static com.example.bookstore.models.response.ErrorResponseMessages.BEARER_TOKEN;


@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenManager implements JwtBase
        , EmailGetter {


    private final SecurityProperties securityProperties;

    @Override
    public String generate(JwtDto jwtDto) {


        Claims claims = Jwts.claims();
        claims.put("email", jwtDto.getEmail());
        claims.put("role",jwtDto.getRole());
        claims.put("type", "REFRESH_TOKEN");

        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getRefreshTokenValidityTime(jwtDto.isRememberMe()));

        return Jwts.builder()
                .setSubject(String.valueOf(jwtDto.getId()))
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

        String typeOfToken = claims.get("type", String.class);

        if (!"REFRESH_TOKEN".equals(typeOfToken)) {
           throw  BaseException.of(BEARER_TOKEN);
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
