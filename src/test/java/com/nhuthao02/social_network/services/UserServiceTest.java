package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.impl.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void whenCreateUser_thenReturnSuccess() {
        // given
        UserCreationRequest request = UserCreationRequest.builder()
                .userName("test")
                .password("test123123")
                .sex(true)
                .build();
        User userResponse = User.builder()
                .id("eyJhbGciOiJIUzUxM")
                .userName("test")
                .password("test123123")
                .sex(true)
                .build();

        Mockito.when(userRepository.existsByUserName(ArgumentMatchers.any()))
                .thenReturn(false);

        Mockito.when(userRepository.save(ArgumentMatchers.any()))
                .thenReturn(userResponse);

        // when
        String id = userService.createUser(request);

        // then
        Assertions.assertThat(id).isEqualTo("eyJhbGciOiJIUzUxM");
    }

    @Test
    public void whenCreateUser_thenReturnException() {
        // given
        UserCreationRequest request = UserCreationRequest.builder()
                .userName("test")
                .password("test123123")
                .sex(true)
                .build();
        User userResponse = User.builder()
                .id("eyJhbGciOiJIUzUxM")
                .userName("test")
                .password("test123123")
                .sex(true)
                .build();

        Mockito.when(userRepository.existsByUserName(ArgumentMatchers.any()))
                .thenReturn(true);

        // when
        var ex = org.junit.jupiter.api.Assertions.assertThrows(
                AppException.class,
                () -> userService.createUser(request));


        // then
        Assertions.assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.USER_EXIST);
        Assertions.assertThat(ex.getErrorCode().getCode()).isEqualTo("001");
        Assertions.assertThat(ex.getErrorCode().getMessage()).isEqualTo("User already exists");
    }
}
