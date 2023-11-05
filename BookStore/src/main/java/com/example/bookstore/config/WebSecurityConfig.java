package com.example.bookstore.config;

import com.company.yellowgo.exception.security.CustomBearerTokenAccessDeniedHandler;
import com.company.yellowgo.exception.security.FilterExceptionHandler;
import com.company.yellowgo.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final FilterExceptionHandler filterExceptionHandler;
    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthorizationFilter authorizationFilter) throws Exception {
        return http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> {
                    // Swagger UI
                    request.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll();
                    // Auth URLs
                    request.requestMatchers("/v1/auth/logout").authenticated();
                    request.requestMatchers("/v1/auth/**").permitAll();
                    request.requestMatchers("/v2/auth/logout").authenticated();
                    request.requestMatchers("/v2/auth/**").permitAll();
//                    company
                    request.requestMatchers("/v1/company/**").hasRole("COMPANY");
                    //user
                    request.requestMatchers("/v1/user/**").hasRole("USER");
//                    car
                    request.requestMatchers("/v1/car/save").hasRole("COMPANY");
                    request.requestMatchers("/v1/car/photo").hasRole("COMPANY");

                    request.requestMatchers("/v1/car/all").permitAll();
                    request.requestMatchers("/v1/car/{number}").permitAll();
                    //rental
                    request.requestMatchers("/v1/rent/**").hasRole("USER");
                    //payment
                    request.requestMatchers("/v1/payment/save").hasRole("USER");
                    request.requestMatchers("/v1/payment/all").hasRole("USER");
                    request.requestMatchers("/v1/payment/test").hasRole("COMPANY");
                    // Test endpoints
                    request.requestMatchers("/test").hasRole("USER");
                    request.requestMatchers("/test/no-auth").permitAll();
                    request.requestMatchers("/test2").permitAll();

                })
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(filterExceptionHandler).accessDeniedHandler(customBearerTokenAccessDeniedHandler))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://yellowgo.co","http://admin.yellowgo.co","http://172.16.0.3:7001",
        "http://localhost:3000","http://127.16.0.3:5001","http://172.16.0.3:4001","http://localhost:3001"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
