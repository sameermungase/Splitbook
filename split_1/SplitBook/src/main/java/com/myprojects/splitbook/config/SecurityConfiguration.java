package com.myprojects.splitbook.config;

import com.myprojects.splitbook.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Using Spring Security version: 6.0.1
@Configuration
@EnableWebSecurity
@EnableMethodSecurity       //To enable @preAuthorize function
public class SecurityConfiguration {

    private final LoginService loginService;

    public SecurityConfiguration(LoginService loginService) {
        this.loginService = loginService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/index","/signup","/signupprocess","/images/**","/static/**","/testservice/**").permitAll()   //Permitting these resources for all users
                //.and().authorizeHttpRequests().requestMatchers("/admin").hasRole("ADMIN")
                .and().authorizeHttpRequests().requestMatchers("/user").hasRole("USER")     // TODO : Need to implement separate endpoints for user/admin, can use this expression for that
                .anyRequest().authenticated()       // any other request MUST be authenticated
                .and().userDetailsService(loginService)     // for login details and authentication process (Spring security will handle that)
                .formLogin()
                .loginPage("/login").permitAll()    //Permit the login-page for all
                .defaultSuccessUrl("/welcome",true)     //Default welcoming page
                .and().logout()
                .logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/login")
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();       //TODO Use a encrypting password encoder later
    }

}
