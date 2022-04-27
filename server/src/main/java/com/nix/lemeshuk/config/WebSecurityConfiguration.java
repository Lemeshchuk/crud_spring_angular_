package com.nix.lemeshuk.config;

import com.nix.lemeshuk.dao.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.nix.lemeshuk.util.Constant.ADMIN_AUTHORITY_NAME;
import static com.nix.lemeshuk.util.Constant.USER_AUTHORITY_NAME;

@Configuration
@ComponentScan("com.nix.lemeshuk")
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final OncePerRequestFilter authenticationTokenFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public final String LOGIN_ENDPOINT = "/api/auth/login";
    public final String REGISTRATION_ENDPOINT = "/api/auth/registration";
    public final String ADMIN_ENDPOINT = "/api/admin/***";
    public final String ROLE_ENDPOINT = "/api/roles";
    public final String USER_ENDPOINT = "/api/user";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       CorsConfiguration corsConfiguration =  new CorsConfiguration().applyPermitDefaultValues();
       corsConfiguration.addAllowedMethod("PUT");
       corsConfiguration.addAllowedMethod("DELETE");

        http
                .cors().configurationSource(request -> corsConfiguration)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT, REGISTRATION_ENDPOINT).not().authenticated()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority(ADMIN_AUTHORITY_NAME)
                .antMatchers(ROLE_ENDPOINT).hasAuthority(ADMIN_AUTHORITY_NAME)
                .antMatchers(USER_ENDPOINT).hasAuthority(USER_AUTHORITY_NAME)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
