package com.sgs.auth.domain.handler;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static com.sgs.auth.domain.utils.SecurityConstants.EXPIRATION_TIME;
import static com.sgs.auth.domain.utils.SecurityConstants.SECRET;

@Component
public class TokenHandler {

    private final SecretKey secretKey;

    public TokenHandler() {
        byte[] decodeKey = BaseEncoding.base64().decode(SECRET);
        secretKey = new SecretKeySpec(decodeKey, 0, decodeKey.length, "AES");
    }

    public Optional<Integer> extractUserId(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional
                    .ofNullable(body.getId())
                    .map(Integer::new);
        } catch (RuntimeException e){
            return Optional.empty();
        }
    }

    public String generateAccessToken(Integer id){
        return Jwts.builder()
                .setId(id.toString())
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Boolean isActiveToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        long tokenExprisionTime = claims.getExpiration().getTime() + EXPIRATION_TIME;
        return tokenExprisionTime >= new Date(System.currentTimeMillis()).getTime();
    }


}
