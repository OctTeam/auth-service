package com.sgs.auth.controller;

import com.sgs.auth.domain.request.TokenCreateRequest;
import com.sgs.auth.domain.response.TokenValidResponse;
import com.sgs.auth.service.AuthService;
import com.sgs.common.exceptions.*;
import com.sgs.common.model.base.model.BaseResponse;
import com.sgs.common.model.user.dto.UserSignInDto;
import com.sgs.common.model.user.dto.UserSignUpDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.sgs.auth.domain.utils.SecurityConstants.HEADER_TOKEN;
import static com.sgs.auth.domain.utils.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
public class AuthController {

    @Autowired private AuthService service;

    @RequestMapping(value = "/auth/token", method = RequestMethod.POST)
    public ResponseEntity createToken(@RequestBody TokenCreateRequest request, HttpServletResponse  response) throws Exception {
        if (request==null || request.getUserId()==null) throw new EmptyRequestException();

        response.addHeader(HEADER_TOKEN, service.createToken(request.getUserId()));
        return new ResponseEntity(OK);
    }

    @RequestMapping(value = "auth/token", method = RequestMethod.GET)
    public ResponseEntity isValidToken(@RequestHeader HttpHeaders headers) throws Exception {
        String token = headers.getFirst(HEADER_TOKEN);
        TokenValidResponse validResponse = new TokenValidResponse();
        validResponse.setValidToken(!service.isEmptyToken(token) && service.isActiveSession(token));
        return new ResponseEntity(validResponse, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> dummyExceptionHandler(Exception e) {
        if (e instanceof NotFoundException)
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        if (e instanceof WrongPassOrEmailException)
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        if (e instanceof EmptyRequestException)
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        if (e instanceof EmailNotValidException)
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        if (e instanceof PasswordDontMatchException)
            return new ResponseEntity<>(new BaseResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new BaseResponse("Unexpected exception"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
