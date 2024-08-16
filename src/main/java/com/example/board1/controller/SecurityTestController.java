package com.example.board1.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {


    @GetMapping("/home")
    public String login() {
        return "Hello";
    }
}
