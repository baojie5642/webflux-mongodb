package org.spring.springboot.service;

import org.spring.springboot.domain.User;
import org.spring.springboot.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<String, User> data = new ConcurrentHashMap<>();

    public UserService() {

    }

    public Flux<User> list() {
        return Flux.fromIterable(data.values());
    }

    public Flux<User> getById(Flux<String> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(data.get(id)));
    }

    public Mono<User> getById(String id) {
        return Mono.justOrEmpty(data.get(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    public Flux<User> createOrUpdate(Flux<User> users) {
        return users.doOnNext(user -> data.put(user.getId(), user));
    }

    public Mono<User> createOrUpdate(User user) {
        data.put(user.getId(), user);
        return Mono.just(user);
    }

    public Mono<User> delete(String id) {
        return Mono.justOrEmpty(data.remove(id));
    }
}
