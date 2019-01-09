package org.spring.springboot.webflux.controller;

import org.spring.springboot.domain.City;
import org.spring.springboot.service.CityService;
import org.spring.springboot.random.LocalRandom;
import org.spring.springboot.random.SecurityRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(value = "/city")
public class BaseController {

    private final CityService citys;

    @Autowired
    public BaseController(CityService citys) {
        if (null == citys) {
            throw new NullPointerException();
        } else {
            this.citys = citys;
        }
    }

    @GetMapping("/helloWorld")
    public Mono<String> sayHelloWorld() {
        return Mono.just("Hello World");
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Mono<City> findCityById(@PathVariable("id") Long id) {
        return citys.findCityById(id);
    }

    @GetMapping()
    @ResponseBody
    public Flux<City> findAllCity() {
        return citys.findAllCity();
    }

    @PostMapping()
    @ResponseBody
    public Mono<City> saveCity(@RequestBody City city) {
        return citys.save(city);
    }

    @PutMapping()
    @ResponseBody
    public Mono<City> modifyCity(@RequestBody City city) {
        return citys.modifyCity(city);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {
        return citys.deleteCity(id);
    }

    @GetMapping("/save/{id}")
    @ResponseBody
    public Mono<City> saveCity(@PathVariable("id") Long id) {
        City c = new City();
        int sr = SecurityRandom.nextInt(1234);
        System.out.println(sr);
        LocalRandom random = LocalRandom.current();
        long ids = random.nextLong(999999);
        c.setId(id + ids);
        c.setCityName("shanghai");
        c.setDescription("SH");
        c.setProvinceId(021L);
        Mono<City> s = citys.save(c);
        return s;
    }

    private static final String CITY_LIST_PATH_NAME = "cityList";
    private static final String CITY_PATH_NAME = "city";

    @GetMapping("/page/list")
    public String listPage(final Model model) {
        final Flux<City> cityFluxList = citys.findAllCity();
        model.addAttribute("cityList", cityFluxList);
        return CITY_LIST_PATH_NAME;
    }

    @GetMapping("/getByName")
    public String getByCityName(final Model model, @RequestParam("cityName") String cityName) {
        final Mono<City> city = citys.getByCityName(cityName);
        model.addAttribute("city", city);
        return CITY_PATH_NAME;
    }

}
