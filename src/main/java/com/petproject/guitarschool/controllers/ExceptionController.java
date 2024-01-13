package com.petproject.guitarschool.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exception")
public class ExceptionController {
    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}
