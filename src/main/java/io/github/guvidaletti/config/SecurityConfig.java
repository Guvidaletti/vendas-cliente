package io.github.guvidaletti.config;

import io.github.guvidaletti.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private UsuarioServiceImpl usuarioService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .userDetailsService(usuarioService)
        .authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers("/cliente/**", "/cliente").hasRole("USER")
                .requestMatchers("/pedido/**").hasRole("USER")
                .requestMatchers("/produto/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/usuario/", "/usuario").permitAll()
                .anyRequest().authenticated()
        )
        .httpBasic(basic -> basic.init(http));
    return http.build();
  }
}

/*

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("gustavo_vidaletti")
        .password("teste123")
        .roles("USER", "ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
 */

/*
  .hasRole("USER");
  .hasAuthority("SCOPE_READ")
  .hasAuthority("SCOPE_WRITE")
  .hasAuthority("SCOPE_DELETE")
  .permitAll();
  .authenticated();
 */