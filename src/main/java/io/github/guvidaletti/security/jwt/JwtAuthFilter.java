package io.github.guvidaletti.security.jwt;

import io.github.guvidaletti.service.impl.UsuarioServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private UsuarioServiceImpl usuarioService;

  public JwtAuthFilter(TokenService tokenService, UsuarioServiceImpl usuarioService) {
    this.tokenService = tokenService;
    this.usuarioService = usuarioService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authorization = request.getHeader("Authorization");
    if (authorization != null && authorization.startsWith("Bearer")) {
      String token = authorization.replace("Bearer ", "");
      boolean valido = tokenService.isTokenValido(token);

      if (valido) {
        String loginUsuario = tokenService.getUsuarioLogado(token);
        UserDetails details = usuarioService.loadUserByUsername(loginUsuario);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(user);
      }
    }
    filterChain.doFilter(request, response);
  }
}
