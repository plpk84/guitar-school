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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping("/register-teacher")
    public String getRegisterForm(Model model){
        RegistrationDto user =new RegistrationDto();
        model.addAttribute("user",user);
        return "admin/register-teacher";
    }
    @PostMapping("/register-teacher")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult bindingResult,
                           Model model){
        if(userService.findByEmail(user.getEmail()).isPresent()){
            return "redirect:/admin/register-teacher?fail";
        }
        if(userService.findByUsername(user.getEmail()).isPresent()){
            return "redirect:/admin/register-teacher?fail";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("user",user);
            return  "admin/register-teacher";
        }
        userService.saveTeacher(user);
        return "redirect:/";
    }
}
