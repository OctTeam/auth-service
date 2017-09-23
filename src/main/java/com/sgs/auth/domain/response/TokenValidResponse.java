package com.sgs.auth.domain.response;

public class TokenValidResponse {

    private boolean validToken;

    public boolean isValidToken() {
        return validToken;
    }

    public void setValidToken(boolean validToken) {
        this.validToken = validToken;
    }
}
