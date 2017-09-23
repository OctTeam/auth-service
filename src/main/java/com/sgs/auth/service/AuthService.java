package com.sgs.auth.service;

import java.util.Optional;

public interface AuthService {
    String createToken(Integer id);
    Optional<Integer> getUserId(String token);
    boolean isActiveSession(String token);
    boolean isEmptyToken(String token);
}
