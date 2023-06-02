package com.llye.mbassignment.config.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.AbstractMap;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String API_TOKEN_HEADER = "api-token";

    @Value("${api.token}")
    public String apiTokenValue;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AbstractMap.SimpleEntry<APIKeyAuthFilter, String[]>[] filters = new AbstractMap.SimpleEntry[]{
                new AbstractMap.SimpleEntry(generateAuthFilter(API_TOKEN_HEADER, apiTokenValue), new String[]{"/jobs/roar-money-disbursements"}),
                new AbstractMap.SimpleEntry(generateAuthFilter(API_TOKEN_HEADER, apiTokenValue), new String[]{"/transactions/*"})
        };

        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();
        for(AbstractMap.SimpleEntry<APIKeyAuthFilter, String[]> filter : filters){
            http.addFilter(filter.getKey()).authorizeRequests().antMatchers(filter.getValue()).authenticated().and();
        }
        http.authorizeRequests()
            .anyRequest().permitAll();
    }

    private static APIKeyAuthFilter generateAuthFilter(String headerKey, String valueKey){
        APIKeyAuthFilter filter = new APIKeyAuthFilter(headerKey);
        filter.setAuthenticationManager(authentication -> {
            String principal = (String) authentication.getPrincipal();
            if (!valueKey.equalsIgnoreCase(principal)) {
                throw new BadCredentialsException("The API key was not found or not the expected value.");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });
        return filter;
    }

}
