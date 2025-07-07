package com.kobyko.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/api/v1/books")
    public String firstAPI(){
        return "Hello World";
    }
}
