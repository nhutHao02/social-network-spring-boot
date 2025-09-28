package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.user.UserCreationRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserLoginRequest;
import com.nhuthao02.social_network.dtos.requests.user.UserUpdateRequest;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.impl.UserService;
import com.nhuthao02.social_network.utils.JwtToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    JwtToken jwtToken;

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

    @Test
    public void whenLogin_thenReturnSuccess() {
        // given
        UserLoginRequest userLogin = UserLoginRequest.builder()
                .userName("test")
                .password("test123123")
                .build();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = User.builder()
                    .userName("test")
                    .password(passwordEncoder.encode("test123123"))
                    .sex(true)
                    .build();
        Optional<User> userOptional = Optional.of(user);

        Mockito.when(userRepository.findByUserName(ArgumentMatchers.any())).thenReturn(userOptional);

        // when
        String tokenRS = userService.login(userLogin);

        // then
        String token = jwtToken.generateToken(userLogin.getUserName());

        Assertions.assertThat(tokenRS).isEqualTo(token);

    }

    @Test
    public void whenLogin_wrongPassword_thenReturnFailure() {
        // given
        UserLoginRequest userLogin = UserLoginRequest.builder()
                .userName("test")
                .password("test123123")
                .build();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = User.builder()
                .userName("test")
                .password(passwordEncoder.encode("test12312333"))
                .sex(true)
                .build();
        Optional<User> userOptional = Optional.of(user);

        Mockito.when(userRepository.findByUserName(ArgumentMatchers.any())).thenReturn(userOptional);

        // when
        String tokenRS = userService.login(userLogin);

        // then

        Assertions.assertThat(tokenRS).isEqualTo("");

    }

    @Test
    public void whenLogin_wrongUserName_thenReturnFailure() {
        // given
        UserLoginRequest userLogin = UserLoginRequest.builder()
                .userName("test")
                .password("test123123")
                .build();
        Optional<User> userOptional = Optional.empty();

        Mockito.when(userRepository.findByUserName(ArgumentMatchers.any())).thenReturn(userOptional);

        // when
        String tokenRS = userService.login(userLogin);

        // then

        Assertions.assertThat(tokenRS).isEqualTo("");

    }

    @Test
    public void whenUpdate_thenReturnSuccess() {
        // given
        UserUpdateRequest request = UserUpdateRequest.builder()
                .fullName("fullName")
                .bio("This is bio")
                .email("email@gmail.com")
                .sex(true)
                .build();
        User user = User.builder()
                .fullName("fullName")
                .bio("This is bio")
                .userName("test")
                .password("test123123")
                .email("email@gmail.com")
                .sex(true)
                .build();
        Optional<User> userOptional = Optional.of(user);

        Mockito.when(userRepository.findByUserName(ArgumentMatchers.any())).thenReturn(userOptional);

        // when
        boolean rs = userService.update("test",request);

        // then
        Assertions.assertThat(rs).isTrue();
    }

    @Test
    public void whenUpdate_wrongUserName_thenReturnException() {
        // given
        UserUpdateRequest request = UserUpdateRequest.builder()
                .fullName("fullName")
                .bio("This is bio")
                .email("email@gmail.com")
                .sex(true)
                .build();
        Optional<User> userOptional = Optional.empty();

        Mockito.when(userRepository.findByUserName(ArgumentMatchers.any())).thenReturn(userOptional);

        // when
        var ex = org.junit.jupiter.api.Assertions.assertThrows(
                AppException.class,
                () -> userService.update("test", request));

        // then
        Assertions.assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
    }



}
