package com.lamp.decoration.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("exceptionController")
@RequestMapping("/exception")
public class ExceptionController {

    @PostMapping("/exception")
    public void exception(){
        throw new RuntimeException("throw runtimne exception ");
    }
}
