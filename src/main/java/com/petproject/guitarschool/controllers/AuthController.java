package com.petproject.guitarschool.controllers;

import com.petproject.guitarschool.dto.RegistrationDto;
import com.petproject.guitarschool.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;


    @GetMapping("/login")
    public String loginPage() {
        return "authentication/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "authentication/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult bindingResult,
                           Model model) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return "redirect:/register?fail";
        }
        if (userService.findByUsername(user.getEmail()).isPresent()) {
            return "redirect:/register?fail";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "authentication/register";
        }
        userService.saveUser(user);
        return "redirect:/login?success=true";
    }

}
