package com.petproject.guitarschool.controllers;

import com.petproject.guitarschool.dto.ApplicationInfoDto;
import com.petproject.guitarschool.models.enums.Role;
import com.petproject.guitarschool.service.ApplicationService;
import com.petproject.guitarschool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final ApplicationService applicationService;

    @GetMapping()
    public String homePage(Model model, Principal principal) {
        String role = null;
        boolean applications = true;
        if (principal != null) {
            List<ApplicationInfoDto> listOfApplications;
            var user = userService.findByUsername(principal.getName()).orElseThrow();
            if (user.getRole().equals(Role.ROLE_ADMIN)) {
                listOfApplications = applicationService.findAllActiveApplications();
            } else if (user.getRole().equals(Role.ROLE_TEACHER)) {
                listOfApplications = applicationService.findByTeacher(user);
            } else {
                listOfApplications = applicationService.findByCreatedBy(user);
            }
            role = user.getRole().name();
            applications = listOfApplications.isEmpty();
            System.out.println(role);
        }
        model.addAttribute("role", role);
        model.addAttribute("applications", applications);
        return "home-page";
    }
}
