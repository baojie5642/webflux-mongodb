package org.spring.springboot.service;

import org.spring.springboot.jpa.CityRepository;
import org.spring.springboot.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CityService {

    private final CityRepository cr;

    @Autowired
    public CityService(CityRepository cr) {
        this.cr = cr;
    }

    public Mono<City> save(City city) {
        Mono<City> s=cr.save(city);
        return s;
    }

    public Mono<City> findCityById(Long id) {
        Mono<City> temp=cr.findById(id);
        return temp;
    }

    public Flux<City> findAllCity() {
        return cr.findAll();
    }

    public Mono<City> modifyCity(City city) {
        return cr.save(city);
    }

    public Mono<Long> deleteCity(Long id) {
        cr.deleteById(id);
        return Mono.create(sink -> sink.success(id));
    }

    public Mono<City> getByCityName(String cityName) {
        return cr.findByCityName(cityName);
    }

}
