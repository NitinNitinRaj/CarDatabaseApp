package com.nr.cardatabase.security;

import com.nr.cardatabase.security.filter.AuthenticationFilter;
import com.nr.cardatabase.security.filter.ExceptionHandlerFilter;
import com.nr.cardatabase.security.filter.JWTAuthorizationFilter;
import com.nr.cardatabase.security.manager.CustomAuthenticationManger;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

  private CustomAuthenticationManger customAuthenticationManger;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationFilter authenticationFilter = new AuthenticationFilter(
      customAuthenticationManger
    );
    authenticationFilter.setFilterProcessesUrl("/authenticate");
    http
      .csrf()
      .disable()
      .cors()
      .and()
      .authorizeRequests()
      .anyRequest()
      .permitAll();
    /*   http
      .headers()
      .frameOptions()
      .disable()
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers("/h2/**")
      .permitAll()
      .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH)
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
      .addFilter(authenticationFilter)
      .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowCredentials(true);
    config.applyPermitDefaultValues();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
