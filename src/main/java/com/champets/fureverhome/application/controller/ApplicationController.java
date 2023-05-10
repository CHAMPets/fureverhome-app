package com.champets.fureverhome.application.controller;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.model.mapper.ApplicationMapper;
import com.champets.fureverhome.application.service.ApplicationService;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.service.PetService;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    @GetMapping("/applications")
    public String listApplications(Model model) {
        List<ApplicationDto> applications = applicationService.findAllApplications();
        model.addAttribute("applications", applications);
        return "application-list";
    }

    @GetMapping("/applications/pet/{petId}")
    public String listApplications(@PathVariable("petId") Long petId, Model model) {
        List<ApplicationDto> applications = applicationService.findApplicationsByPetId(petId);
        model.addAttribute("applications", applications);
        return "application-list";
    }

    @GetMapping("/applications/{applicationId}")
    public String applicationDetail(@PathVariable("applicationId") Long applicationId, Model model) {
        ApplicationDto application = applicationService.findApplicationById(applicationId);
        model.addAttribute("applicationDto", application);
        model.addAttribute("pet", application.getPet());
        model.addAttribute("user", application.getUser());
        return "application-details";
    }

    @PostMapping("/applications/{petId}/{userId}")
    public String saveApplication(@ModelAttribute("application") ApplicationDto applicationDto, @PathVariable("petId") Long petId, @PathVariable("userId") Long userId, Model model) {
        applicationService.saveApplication(applicationDto, petId, userId);
        return "redirect:/applications";
    }

    @GetMapping("/applications/{applicationId}/edit")
    public String editApplicationForm(@PathVariable("applicationId") Long applicationId, Model model) {
        ApplicationDto application = applicationService.findApplicationById(applicationId);
        model.addAttribute("application", application);
        return "application-edit";
    }

    @PostMapping("/applications/{applicationId}/edit")
    public String updateApplication(@PathVariable("applicationId") Long applicationId,
                                    @Valid @ModelAttribute("application") ApplicationDto application,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("application", application);
            return "application-edit";
        }
        ApplicationDto applicationDto = applicationService.findApplicationById(applicationId);
        application.setId(applicationId);
        application.setPet(applicationDto.getPet());
        application.setUser(applicationDto.getUser());
        applicationService.updateApplication(application);
        return "redirect:/applications";
    }
}