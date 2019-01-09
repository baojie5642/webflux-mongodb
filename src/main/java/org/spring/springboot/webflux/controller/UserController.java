package org.spring.springboot.webflux.controller;

import org.spring.springboot.domain.User;
import org.spring.springboot.exception.ResourceNotFoundException;
import org.spring.springboot.random.LocalRandom;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService user;

    @Autowired
    public UserController(final UserService user) {
        this.user = user;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {
    }

    @GetMapping("/list")
    public Flux<User> list() {
        return user.list();
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") final String id) {
        return user.getById(id);
    }

    @PostMapping("/create")
    public Flux<User> create(@RequestBody final Flux<User> users) {
        return user.createOrUpdate(users);
    }

    @GetMapping("/add")
    public Mono<User> update() {
        LocalRandom random = LocalRandom.current();
        String id=random.nextInt(774455693)+"";
        final User u = new User();
        u.setId(id);

        u.setName(id + random.nextInt(99999));
        u.setEmail("baojie@baojie.com");
        return user.createOrUpdate(u);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<User> delete(@PathVariable("id") final String id) {
        return user.delete(id);
    }
}
