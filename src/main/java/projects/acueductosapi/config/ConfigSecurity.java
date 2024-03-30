package projects.acueductosapi.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projects.acueductosapi.filter.JwtReqFilter;

import javax.sql.DataSource;


@Configuration
public class ConfigSecurity {

    @Autowired
    @Lazy
    private JwtReqFilter jwtReqFilter;

    @Bean
    public UserDetailsManager userDetailsManager(DataSource datasource) {
        return new JdbcUserDetailsManager(datasource);

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configure -> {
            configure
                    .requestMatchers(HttpMethod.POST, "/productos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/usuarios/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/quotes").hasRole("ADMIN")
                    .requestMatchers("/usuarios","/orders","/quotes","/usuarios/**","/authenticate","/productos/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
        })

                     .addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class)
                     .sessionManagement( (session) -> session
                     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );




        http.httpBasic(Customizer.withDefaults());

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }






}
