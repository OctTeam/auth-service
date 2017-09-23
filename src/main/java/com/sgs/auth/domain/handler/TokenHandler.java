package com.sgs.auth.domain.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.sgs.auth.domain.utils.SecurityConstants.EXPIRATION_TIME;
import static com.sgs.auth.domain.utils.SecurityConstants.SECRET;

@Component
public class TokenHandler {

    private byte[] secretKey;

    public TokenHandler() {
        secretKey = TextCodec.BASE64.decode(SECRET);
    }

    private Optional<Jws<Claims>> parseJwsClaims(String token) {
        try {
            return Optional.ofNullable(Jwts.parser()
                                        .requireSubject("session-token")
                                        .setSigningKey(secretKey)
                                        .parseClaimsJws(token));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Integer> extractUserId(String token) {
        Optional<Jws<Claims>> jws = parseJwsClaims(token);
        if (!jws.isPresent()) return Optional.empty();
        return Optional
                .ofNullable(jws.get().getBody().getId())
                .map(Integer::new);
    }

    public String generateAccessToken(Integer id){
        return Jwts.builder()
                .setSubject("session-token")
                .claim("user_id", id.toString())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Boolean isActiveToken(String token) {
        Optional<Jws<Claims>> jws = parseJwsClaims(token);
        if (!jws.isPresent()) return false;

        long tokenExpiration = jws.get().getBody().getExpiration().getTime();
        return tokenExpiration >= new Date(System.currentTimeMillis()).getTime();
    }


}
