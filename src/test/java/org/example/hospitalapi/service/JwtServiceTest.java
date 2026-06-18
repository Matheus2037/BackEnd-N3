package org.example.hospitalapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.hospitalapi.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

  private final JwtService jwtService = new JwtService();

  private User buildUser(String username) {
    User user = new User();
    user.setUsername(username);
    user.setPassword("encoded-password");
    return user;
  }

  @Test
  void shouldGenerateTokenForUser() {
    User user = buildUser("fernanda");

    String token = jwtService.generateToken(user);

    assertThat(token).isNotNull();
  }

  @Test
  void shouldExtractUsernameFromToken() {
    User user = buildUser("fernanda");
    String token = jwtService.generateToken(user);

    String extractedUsername = jwtService.extractUsername(token);

    assertThat(extractedUsername).isEqualTo(user.getUsername());
  }

  @Test
  void shouldValidateTokenForCorrectUser() {
    User user = buildUser("fernanda");
    String token = jwtService.generateToken(user);

    boolean isValid = jwtService.isTokenValid(token, user);

    assertThat(isValid).isTrue();
  }

  @Test
  void shouldInvalidateTokenForWrongUser() {
    User userA = buildUser("fernanda");
    User userB = buildUser("carlos");
    String token = jwtService.generateToken(userA);

    boolean isValid = jwtService.isTokenValid(token, userB);

    assertThat(isValid).isFalse();
  }
}
