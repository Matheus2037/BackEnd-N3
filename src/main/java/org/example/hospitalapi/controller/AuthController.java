package org.example.hospitalapi.controller;

import jakarta.validation.Valid;
import org.example.hospitalapi.dtos.AuthResponseDto;
import org.example.hospitalapi.dtos.RegisterRequestDto;
import org.example.hospitalapi.entity.User;
import org.example.hospitalapi.exceptions.ConflictException;
import org.example.hospitalapi.repository.UserRepository;
import org.example.hospitalapi.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
    userRepository.findByUsername(dto.username()).ifPresent(existingUser -> {
      throw new ConflictException("Username already exists");
    });

    User user = new User();
    user.setUsername(dto.username());
    user.setPassword(passwordEncoder.encode(dto.password()));

    user = userRepository.save(user);

    String token = jwtService.generateToken(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDto(token));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody RegisterRequestDto dto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

    User user = userRepository.findByUsername(dto.username()).orElseThrow();

    String token = jwtService.generateToken(user);
    return ResponseEntity.ok(new AuthResponseDto(token));
  }
}
