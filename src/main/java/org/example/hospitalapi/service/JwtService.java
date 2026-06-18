package org.example.hospitalapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final String secretKey =
      "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .signWith(getSignInKey(), Jwts.SIG.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername());
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return new SecretKeySpec(keyBytes, "HmacSHA256");
  }
}
