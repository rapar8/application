/*
package com.mywarehouse.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Uncomment this file allong with application.yml
 *


@Profile({"!dev & !local & !test"})
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource;

    public SecurityConfig(UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource) {
        this.urlBasedCorsConfigurationSource = urlBasedCorsConfigurationSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .cors()
                .configurationSource(urlBasedCorsConfigurationSource)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}
*/
