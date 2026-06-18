package org.example.hospitalapi.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.hospitalapi.dtos.RegisterRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String registerAndGetToken(String username, String password) throws Exception {
    RegisterRequestDto dto = new RegisterRequestDto(username, password);

    MvcResult result = mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andReturn();

    JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
    return response.get("token").asText();
  }

  @Test
  void shouldRegisterUserAndReturnToken() throws Exception {
    RegisterRequestDto dto = new RegisterRequestDto("fernanda", "senha123");

    MvcResult result = mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andReturn();

    JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
    assertThat(response.get("token").asText()).isNotBlank();
  }

  @Test
  void shouldReturnConflictWhenUsernameAlreadyExists() throws Exception {
    RegisterRequestDto dto = new RegisterRequestDto("carlos", "senha123");

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated());

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isConflict());
  }

  @Test
  void shouldLoginAndReturnToken() throws Exception {
    RegisterRequestDto dto = new RegisterRequestDto("mariana", "senha123");

    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated());

    MvcResult result = mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andReturn();

    JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
    assertThat(response.get("token").asText()).isNotBlank();
  }

  @Test
  void shouldReturnUnauthorizedWhenNoToken() throws Exception {
    mockMvc.perform(get("/doctor"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void shouldReturnOkWhenValidToken() throws Exception {
    String token = registerAndGetToken("pedro", "senha123");

    mockMvc.perform(get("/doctor").header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }
}
