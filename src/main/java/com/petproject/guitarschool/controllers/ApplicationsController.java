package com.petproject.guitarschool.controllers;

import com.petproject.guitarschool.dto.ApplicationDto;
import com.petproject.guitarschool.dto.ApplicationInfoDto;
import com.petproject.guitarschool.dto.SlotDto;
import com.petproject.guitarschool.models.enums.InstrumentType;
import com.petproject.guitarschool.models.enums.Role;
import com.petproject.guitarschool.service.ApplicationService;
import com.petproject.guitarschool.service.SlotService;
import com.petproject.guitarschool.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationsController {

    private final ApplicationService applicationService;
    private final UserService userService;
    private final SlotService slotService;

    @GetMapping()
    public String applications(Model model, Principal principal) {
        var user = userService.findByUsername(principal.getName()).orElseThrow();
        List<ApplicationInfoDto> applications;
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            applications = applicationService.findAllActiveApplications();
        } else if (user.getRole().equals(Role.ROLE_TEACHER)) {
            applications = applicationService.findByTeacher(user);
        } else {
            applications = applicationService.findByCreatedBy(user);
        }
        model.addAttribute("role", user.getRole().name());
        model.addAttribute("applications", applications);
        return "applications/applications";
    }

    @GetMapping("/new")
    public String create(Model model, Principal principal) {
        var application = ApplicationDto.builder().build();
        final var user = userService.findByUsername(principal.getName()).orElseThrow();
        application.setUserId(user.getId());
        model.addAttribute("slots", slotService.findAllActiveSlots());
        model.addAttribute("instruments", List.of(InstrumentType.UKULELE, InstrumentType.ACOUSTIC, InstrumentType.ELECTRO));
        model.addAttribute("application", application);
        model.addAttribute("role", user.getRole().name());
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            model.addAttribute("users", userService.findAllActiveUsers());
        }
        return "applications/add-application";
    }


    @PostMapping("/new")
    public String save(@ModelAttribute("application") @Valid ApplicationDto application,
                       @ModelAttribute("instruments") ArrayList<InstrumentType> instruments,
                       @ModelAttribute("slots") ArrayList<SlotDto> slots,
                       @ModelAttribute("role") String role,
                       BindingResult bindingResult,
                       Model model) {
        System.out.println("inside save method");
        if (bindingResult.hasErrors()) {
            model.addAttribute("slots", slots);
            model.addAttribute("instruments", instruments);
            model.addAttribute("application", application);
            model.addAttribute("role", role);
            if (role.equals(Role.ROLE_ADMIN.name())) {
                model.addAttribute("users", userService.findAllActiveUsers());
            }
            return "applications/add-application";
        }
        applicationService.saveApplication(application);
        return "redirect:/applications";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id, Principal principal) {
        final var application = applicationService.findById(id).orElseThrow();
        final var user = userService.findByUsername(principal.getName()).orElseThrow();
        if (!application.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to edit this application");
        }
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            model.addAttribute("users", userService.findAllActiveUsers());
        }
        model.addAttribute("app", application);
        model.addAttribute("slots", slotService.findAllActiveSlots());
        model.addAttribute("instruments", List.of(InstrumentType.UKULELE, InstrumentType.ACOUSTIC, InstrumentType.ELECTRO));
        model.addAttribute("role", user.getRole().name());
        return "applications/edit-application";
    }


    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("app") @Valid ApplicationDto application,
                         @ModelAttribute("instruments") ArrayList<InstrumentType> instruments,
                         @ModelAttribute("instruments") ArrayList<SlotDto> slots,
                         @ModelAttribute("role") String role,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            if (role.equals(Role.ROLE_ADMIN.name())) {
                model.addAttribute("users", userService.findAllActiveUsers());
            }
            model.addAttribute("app", application);
            model.addAttribute("instruments", instruments);
            model.addAttribute("slots", slots);
            return "applications/edit-application";
        }
        applicationService.updateApplication(application);
        return "redirect:/applications";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        applicationService.deleteById(id);
        return "redirect:/applications";
    }
}
