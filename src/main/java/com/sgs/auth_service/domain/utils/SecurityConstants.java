package com.sgs.auth_service.domain.utils;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_TOKEN = "Authorization";


    public static final String SIGN_UP_URL = "/auth/register";
    public static final String SIGN_IN_URL = "/auth/login";
    public static final String[] SIGN_URLS = {SIGN_IN_URL, SIGN_UP_URL};
}
