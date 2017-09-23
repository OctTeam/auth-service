package com.sgs.auth.service.impl;

import com.sgs.auth.domain.handler.TokenHandler;
import com.sgs.auth.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static com.sgs.auth.domain.utils.SecurityConstants.EXPIRATION_TIME;
import static com.sgs.auth.domain.utils.SecurityConstants.SECRET;

@Service
public class AuthServiceImpl implements AuthService {

    private final TokenHandler tokenHandler;

    @Autowired
    public AuthServiceImpl(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    public String createToken(Integer id) {
        return tokenHandler.generateAccessToken(id);
    }

    @Override
    public boolean isActiveSession(String token) {
        return tokenHandler.isActiveToken(token);
    }

    @Override
    public Optional<Integer> getUserId(String token) {
        return tokenHandler.extractUserId(token);
    }

    @Override
    public boolean isEmptyToken(final String token){
        return token==null && token.isEmpty();
    }
}
