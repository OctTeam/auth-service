package com.sgs.auth.service.impl;

import com.sgs.auth.domain.handler.TokenHandler;
import com.sgs.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
