package com.sgs.auth.controller;

import com.sgs.auth.domain.request.TokenCreateRequest;
import com.sgs.auth.domain.response.TokenValidResponse;
import com.sgs.auth.service.AuthService;
import com.sgs.common.exceptions.*;
import com.sgs.common.model.base.model.BaseResponse;
import com.sgs.common.utils.SgsExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.sgs.auth.domain.utils.SecurityConstants.HEADER_TOKEN;
import static com.sgs.common.utils.SgsCommonUtils.isEmpty;
import static com.sgs.common.utils.SgsExceptionUtils.responseData;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class AuthController {

    @Autowired private AuthService service;

    @RequestMapping(value = "/auth/token", method = RequestMethod.POST)
    public void createToken(@RequestBody TokenCreateRequest request, HttpServletResponse  response) throws Exception {
        if (isEmpty(request)) throw new EmptyRequestException();
        response.addHeader(HEADER_TOKEN, service.createToken(request.getUserId()));
    }

    @RequestMapping(value = "auth/token", method = RequestMethod.GET)
    public ResponseEntity isValidToken(@RequestHeader HttpHeaders headers) throws Exception {
        String token = headers.getFirst(HEADER_TOKEN);
        TokenValidResponse validResponse = new TokenValidResponse();
        validResponse.setValidToken(!service.isEmptyToken(token) && service.isActiveSession(token));
        return ResponseEntity.ok(validResponse);
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse> dummyExceptionHandler(Exception e) {
        SgsExceptionUtils.ResponseData data = responseData(e);
        return new ResponseEntity<>(data.getBody(), HttpStatus.valueOf(data.getCode()));
    }

}
