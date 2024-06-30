package io.github.guvidaletti.security.jwt;

import io.github.guvidaletti.domain.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class TokenService {
  /**
   * Em segundos
   */
  @Value("${security.jwt.expiracao}")
  private String expiracao;

  @Value("${security.jwt.chave-assinatura}")
  private String chave;

  public String geraToken(Usuario usuario) {
    long expString = Long.parseLong(expiracao);
    LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusSeconds(expString);
    Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
    Date data = Date.from(instant);

    String[] roles = usuario.isAdmin() ? new String[]{
        "ADMIN", "USER"
    } : new String[]{
        "USER"
    };


    return Jwts.builder()
        .setSubject(usuario.getUsuario())
        .setExpiration(data)
        .claim("roles", roles)
        .claim("ldap", usuario.getUsuario())
        .signWith(SignatureAlgorithm.HS512, chave)
        .compact();
  }

  private Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(chave).parseClaimsJws(token).getBody();
  }

  public boolean isTokenValido(String token) throws ExpiredJwtException {
    try {
      Claims claims = getClaims(token);
      Date dataExpiracao = claims.getExpiration();
      LocalDateTime expiracao = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
      return LocalDateTime.now().isBefore(expiracao);
    } catch (Exception e) {
      return false;
    }
  }

  public String getUsuarioLogado(String token) throws ExpiredJwtException {
    return getClaims(token).getSubject();
  }

  public String[] getRoles(String token) throws ExpiredJwtException {
    return (String[]) getClaims(token).get("roles");
  }
}
