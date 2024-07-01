package io.github.guvidaletti.config;

import io.github.guvidaletti.security.jwt.JwtAuthFilter;
import io.github.guvidaletti.security.jwt.TokenService;
import io.github.guvidaletti.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private UsuarioServiceImpl usuarioService;

  @Autowired
  private TokenService tokenService;


  @Bean
  public OncePerRequestFilter jwtFilter() {
    return new JwtAuthFilter(tokenService, usuarioService);
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(
        "/v3/api-docs/**",
        "/swagger-ui/**"
    );
  }

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
                .requestMatchers(HttpMethod.POST, "/usuario/login", "/usuario/login/").permitAll()
                .anyRequest().authenticated()
        ).sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

/*
.httpBasic(basic -> basic.init(http));

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