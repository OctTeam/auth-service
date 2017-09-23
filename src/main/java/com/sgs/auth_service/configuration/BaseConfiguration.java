package com.sgs.auth_service.configuration;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfiguration {

    @Bean
    public EmailValidator emailValidator(){
        return EmailValidator.getInstance();
    }

}
