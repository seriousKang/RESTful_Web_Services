package com.example.restfulwebservice.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource ms;

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "hello, world!";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("hello, world!");
    }

    /**
     * PathVariable
     */
    @GetMapping("/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("hello, %s world!", name));
    }

    @GetMapping("/hello-world-i18n")
    public String helloWorldI18n(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
        log.info("locale = {}", locale);
        return ms.getMessage("greeting.message", null, locale);
    }
}
