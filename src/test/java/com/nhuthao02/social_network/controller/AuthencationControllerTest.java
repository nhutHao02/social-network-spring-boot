package com.nhuthao02.social_network.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserLoginRequest;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.services.IUserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthencationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Test
    public void whenSignUp_thenReturnSuccess() throws Exception {
        // given
        UserCreationRequest request = UserCreationRequest.builder()
                .userName("test")
                .password("test123123")
                .sex(true)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn("id");

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Successfully"))
        ;

    }

    @Test
    public void whenSignUp_thenReturnUserNameExist() throws Exception {
        // given
        UserCreationRequest request = UserCreationRequest.builder()
                .userName("test")
                .password("test123123")
                .sex(true)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenThrow(new AppException(ErrorCode.USER_EXIST));

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("001"))
                .andExpect(jsonPath("$.message").value("User already exists"))
        ;

    }

    @Test
    public void whenLogin_thenReturnSuccess() throws Exception {
        // given
        UserLoginRequest request = UserLoginRequest.builder()
                .userName("test")
                .password("test123123")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.login(ArgumentMatchers.any())).thenReturn("eyJhbGciOiJIUzUxM");

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Successfully"))
                .andExpect(jsonPath("$.data.token").value("eyJhbGciOiJIUzUxM"));

    }

    @Test
    public void whenLogin_thenReturnFailure() throws Exception {
        // given
        UserLoginRequest request = UserLoginRequest.builder()
                .userName("test")
                .password("test123123")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.login(ArgumentMatchers.any())).thenReturn("");

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.code").value("500"))
                .andExpect(jsonPath("$.message").value("Failure"));
    }
}
