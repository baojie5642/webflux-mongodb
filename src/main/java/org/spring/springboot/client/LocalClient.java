package org.spring.springboot.client;

import org.spring.springboot.domain.City;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LocalClient {
    private final WebClient webClient = WebClient.create("http://localhost:8080");

    public Mono<City> findById(Long userId) {
        return webClient
                .get()
                .uri("/city/" + userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(cr -> cr.bodyToMono(City.class));
    }

    public static void main(String args[]){
        LocalClient client=new LocalClient();
        Mono<City> c=client.findById(9876L);
        c.subscribe(m->System.out.println(m));
    }
}
