package com.raman.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public  String sayHello() {
        return "Hello";
    }

    @GetMapping("/helloName{id}")
    public  String sayHelloName(@PathVariable String id) {
        return "Hello "  + id;
    }
}
