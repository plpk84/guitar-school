package com.petproject.guitarschool.controllers;

import com.petproject.guitarschool.dto.SlotDto;
import com.petproject.guitarschool.dto.UserInfoDto;
import com.petproject.guitarschool.models.enums.Role;
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
@RequestMapping("/slots")
public class SlotsController {
    private final SlotService slotService;
    private final UserService userService;

    @GetMapping()
    public String slots(Model model, Principal principal) {
        var user = userService.findByUsername(principal.getName()).orElseThrow();
        List<SlotDto> slots;
        if (user.getRole().equals(Role.ROLE_ADMIN)) {
            slots = slotService.findAllActiveSlots();
        } else {
            slots = slotService.findSlotsByTeacher(user);
        }
        model.addAttribute("role",user.getRole().name());
        model.addAttribute("slots", slots);
        return "slots/slots";
    }

    @GetMapping("/new")
    public String newSlot(Model model, Principal principal) {
        SlotDto newSlot = new SlotDto();
        var user = userService.findByUsername(principal.getName()).orElseThrow();
        List<UserInfoDto> teachers= new ArrayList<>();
        if(user.getRole().equals(Role.ROLE_ADMIN)) {
            teachers=userService.findAllActiveTeachers();
        }
        model.addAttribute("teachers",teachers);
        model.addAttribute("role",user.getRole().name());
        newSlot.setCreatedById(user.getId());
        model.addAttribute("slot", newSlot);
        return "slots/add-slot";
    }

    @PostMapping("/new")
    public String addSlot(@ModelAttribute("slot") @Valid SlotDto slotDto,
                          @ModelAttribute("role") String role,
                          @ModelAttribute("teachers") ArrayList <UserInfoDto> teachers,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("slot", slotDto);
            model.addAttribute("teachers",teachers);
            model.addAttribute("role",role);
            return "slots/add-slot";
        }
        slotService.saveSlot(slotDto);
        return "redirect:/slots";
    }
    @GetMapping("/{id}/edit")
    public String editSlot(Model model, @PathVariable("id") Long id,Principal principal){
        var slot = slotService.findById(id).orElseThrow();
        var user = userService.findByUsername(principal.getName()).orElseThrow();
        if(!slot.getCreatedById().equals(user.getId())&&!user.getRole().equals(Role.ROLE_ADMIN)){
            throw new AccessDeniedException("You do not have permission to edit this slot");
        }
        List<UserInfoDto> teachers= new ArrayList<>();
        if(user.getRole().equals(Role.ROLE_ADMIN)) {
            teachers=userService.findAllActiveTeachers();
        }
        model.addAttribute("teachers",teachers);
        model.addAttribute("role",user.getRole().name());
        model.addAttribute("slot",slot);
        return "slots/edit-slot";
    }
    @PostMapping("/{id}/edit")
    public String doEdit(@PathVariable("id") Long id,
                         @ModelAttribute("slot") @Valid SlotDto slot,
                         @ModelAttribute("role") String role,
                         @ModelAttribute("teachers") ArrayList <UserInfoDto> teachers,
                         BindingResult bindingResult,
                         Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("teachers",teachers);
            model.addAttribute("role",role);
            model.addAttribute("slot",slot);
            return "slots/edit-slot";
        }
        slotService.updateSlot(slot);
        return "redirect:/slots";
    }
    @PostMapping("/{id}/delete")
    public String doDelete(@PathVariable("id") Long id ){
        slotService.deleteById(id);
        return "redirect:/slots";
    }
}
