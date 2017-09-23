package com.sgs.auth_service.controller;

import com.sgs.common.model.user.dto.UserSignInDto;
import com.sgs.common.model.user.dto.UserSignUpDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.sgs.auth_service.domain.utils.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.boot.web.filter.ApplicationContextHeaderFilter.HEADER_NAME;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController("/auth")
public class AuthController {

    @Autowired private EmailValidator emailValidator;

    private ResponseEntity response(HttpStatus status){
        return new ResponseEntity(status);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody UserSignInDto body) {



        return response(CREATED);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody UserSignUpDto doby) {


        return response(CREATED);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst(HEADER_NAME);
        if (token == null || !token.startsWith(TOKEN_PREFIX))
            return response(UNAUTHORIZED);

        headers.add(HEADER_NAME, null);
        return response(OK);
    }

}
