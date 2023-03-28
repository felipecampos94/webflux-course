package br.com.webfluxcourse.unit.service;

import br.com.webfluxcourse.entity.User;
import br.com.webfluxcourse.entity.model.request.UserRequest;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.repository.UserRepository;
import br.com.webfluxcourse.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        UserRequest request = new UserRequest("Tekomu Nakama", "tekomu.nakama@mail.com", "123");
        User entity = User.builder().build();

        when(mapper.toEntity(any(UserRequest.class))).thenReturn(entity);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = userService.save(request);

        StepVerifier.create(result).expectNextMatches(Objects::nonNull).expectComplete().verify();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findById() {
        when(userRepository.findById(anyString())).thenReturn(Mono.just(User.builder().id("1234").build()));

        Mono<User> result = userService.findById("123");

        StepVerifier.create(result).expectNextMatches(user -> user.getClass() == User.class).expectComplete().verify();

        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(User.builder().build()));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result).expectNextMatches(user -> user.getClass() == User.class).expectComplete().verify();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void update() {
        UserRequest request = new UserRequest("Tekomu Nakama", "tekomu.nakama@mail.com", "123");
        User entity = User.builder().build();

        when(mapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(entity);
        when(userRepository.findById(anyString())).thenReturn(Mono.just(entity));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(entity));

        Mono<User> result = userService.update("123", request);

        StepVerifier.create(result).expectNextMatches(Objects::nonNull).expectComplete().verify();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void delete() {
    }
}