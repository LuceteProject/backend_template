package com.lucete.template.info.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {
    @GetMapping("/home")
    public String test() {
        return "static/index.html";

    }
}
