package br.com.webfluxcourse.service;

import br.com.webfluxcourse.entity.User;
import br.com.webfluxcourse.entity.model.request.UserRequest;
import br.com.webfluxcourse.mapper.UserMapper;
import br.com.webfluxcourse.repository.UserRepository;
import br.com.webfluxcourse.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public Mono<User> save(final UserRequest request) {
        return userRepository.save(mapper.toEntity(request));
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ObjectNotFoundException(
                                format("Object Not Found! Id: %s Type %s", id, User.class.getSimpleName())
                        )
                ));
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> update(final String id, final UserRequest request){
        return this.findById(id).map(entity -> mapper.toEntity(request, entity))
                .flatMap(userRepository::save);
    }
}
