package com.champets.fureverhome.application.controller;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.model.mapper.ApplicationMapper;
import com.champets.fureverhome.application.service.ApplicationService;
import com.champets.fureverhome.application.service.MailService;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.pet.service.PetService;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.dto.RegistrationDto;
import com.champets.fureverhome.user.model.dto.UserDto;
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
    @Autowired
    private MailService mailService;

    @GetMapping("/applications")
    public String listApplications(Model model) {
        List<ApplicationDto> applications = applicationService.findAllApplications();
        model.addAttribute("applications", applications);
        return "admin/application-list";
    }

    @GetMapping("/applications/pet/{petId}")
    public String listApplications(@PathVariable("petId") Long petId, Model model) {
        List<ApplicationDto> applications = applicationService.findApplicationsByPetId(petId);
        model.addAttribute("applications", applications);
        return "admin/application-list";
    }

    @GetMapping("/applications/{applicationId}")
    public String applicationDetail(@PathVariable("applicationId") Long applicationId, Model model) {
        ApplicationDto application = applicationService.findApplicationById(applicationId);
        model.addAttribute("applicationDto", application);
        model.addAttribute("pet", application.getPet());
        model.addAttribute("user", application.getUser());
        return "admin/application-details";
    }

    @PostMapping("/applications/{petId}/{userId}")
    public String saveApplication(@ModelAttribute("application") ApplicationDto applicationDto, @PathVariable("petId") Long petId, @PathVariable("userId") Long userId, Model model) {
        applicationService.saveApplication(applicationDto, petId, userId);
        UserDto userDto = userService.findUserById(userId);
        PetDto petDto = petService.findPetById(petId);
        petDto.setApplicationCounter(petDto.getApplicationCounter() + 1);
        petService.updatePet(petDto);
        mailService.sendEmail(userDto.getEmail(), "Application Pending", "Your application for pet " + petDto.getName() + " is under review.");

        return "redirect:/applications";
    }

    @PostMapping("/applications/{applicationId}")
    public String updateApplication(@PathVariable("applicationId") Long applicationId,
                                    @ModelAttribute("application") ApplicationDto application,
                                    BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("applicationDto", application);
//            return "admin/application-details";
//        }
        ApplicationDto applicationDto = applicationService.findApplicationById(applicationId);
        application.setId(applicationId);
        application.setUser(applicationDto.getUser());
        switch (applicationDto.getApplicationStatus()) {
            case APPROVED:
                mailService.sendEmail(application.getUser().getEmail(), "Application Approved", "Your application for pet " + applicationDto.getPet().getName() + " is approved.");
                break;
            case REJECTED:
                mailService.sendEmail(application.getUser().getEmail(), "Application Rejected", "Your application for pet " + applicationDto.getPet().getName() + " is rejected.");
                break;
            case RELEASED:
                applicationDto.getPet().setApplicationCounter(0);
                applicationDto.getPet().setActive(false);
                break;
            case CANCELLED:
                applicationDto.getPet().setApplicationCounter(applicationDto.getPet().getApplicationCounter() - 1);
                applicationDto.getPet().setActive(false);
                mailService.sendEmail(application.getUser().getEmail(), "Application Cancelled", "Your application for pet " + applicationDto.getPet().getName() + " is cancelled.");
                break;
        }
        application.setPet(applicationDto.getPet());
        applicationService.updateApplication(application);

        return "redirect:/applications";
    }
}