package org.eris.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/general")
public class General {
    @GetMapping("/healthcheck")
    public String validateHealth(){
        return "Okay";
    }
    @GetMapping("/ping")
    public String validatePing(){
        return "Pong";
    }
}
