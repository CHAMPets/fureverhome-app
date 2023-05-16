package com.champets.fureverhome.application.controller;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.model.mapper.ApplicationMapper;
import com.champets.fureverhome.application.service.ApplicationService;
import com.champets.fureverhome.application.service.MailService;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.pet.model.mapper.PetMapper;
import com.champets.fureverhome.pet.service.PetService;
import com.champets.fureverhome.user.model.UserEntity;
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

import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPetDto;

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

    @GetMapping("/admin/applications")
    public String listApplications(Model model) {
        List<ApplicationDto> applications = applicationService.findAllApplications();
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("applications", applications);
        return "admin/application-list";
    }

    @GetMapping("/admin/applications/pet/{petId}")
    public String listApplications(@PathVariable("petId") Long petId, Model model) {
        List<ApplicationDto> applications = applicationService.findApplicationsByPetId(petId);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("applications", applications);
        return "admin/application-list";
    }

    @GetMapping("/applications")
    public String listUserApplications(Model model) {
        UserEntity user = userService.getCurrentUser();

        List<ApplicationDto> applications = applicationService.findApplicationsByUserId(user.getId());
        model.addAttribute("applications", applications);
        model.addAttribute("user", user);
        return "user/application-list";
    }

    @GetMapping("/admin/applications/{applicationId}")
    public String applicationDetail(@PathVariable("applicationId") Long applicationId, Model model) {
        ApplicationDto application = applicationService.findApplicationById(applicationId);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("applicationDto", application);
//        model.addAttribute("petApp", application.getPet());
//        model.addAttribute("userApp", application.getUser());
        return "admin/application-details";
    }

    @PostMapping("/applications/{petId}")
    public String saveApplication(@ModelAttribute("application") ApplicationDto applicationDto, @PathVariable("petId") Long petId, Model model) {
        UserEntity user = userService.getCurrentUser();
        applicationService.saveApplication(applicationDto, petId, user.getId());
        UserDto userDto = userService.findUserById(user.getId());
        PetDto petDto = petService.findPetById(petId);
        petDto.setApplicationCounter(petDto.getApplicationCounter() + 1);
        petService.updatePet(petDto);
        mailService.sendEmail(userDto.getEmail(), "Application #" + applicationDto.getId() + " Pending", "Your application for pet " + petDto.getName() + " is under review. Kindly respond by providing your documents.");

        return "redirect:/applications";
    }

    @PostMapping("/admin/applications/{applicationId}")
    public String updateApplication(@PathVariable("applicationId") Long applicationId,
                                    @ModelAttribute("application") ApplicationDto application,
                                    Model model) {
        ApplicationDto applicationDto = applicationService.findApplicationById(applicationId);
        application.setId(applicationId);
        application.setUser(applicationDto.getUser());
        switch (applicationDto.getApplicationStatus()) {
            case APPROVED:
                mailService.sendEmail(application.getUser().getEmail(), "Application #" + applicationDto.getId() + " Approved", "Your application for pet " + applicationDto.getPet().getName() + " is approved. Drop by our shelter and meet your furry friend.");
                break;
            case REJECTED:
                applicationDto.getPet().setApplicationCounter(applicationDto.getPet().getApplicationCounter() - 1);
                mailService.sendEmail(application.getUser().getEmail(), "Application #" + applicationDto.getId() + " Rejected", "We regret to inform that your application for pet " + applicationDto.getPet().getName() + " is rejected.");
                break;
            case RELEASED:
                applicationDto.getPet().setApplicationCounter(0);
                applicationDto.getPet().setActive(false);
                petService.savePet(mapToPetDto(applicationDto.getPet()));
                mailService.sendEmail(application.getUser().getEmail(), "Pet Released", "The pet " + applicationDto.getPet().getName() + " has been released. Thank you for your interest in adopting. We appreciate your support for our shelter and encourage you to continue considering our other adorable pets in the future.");
                break;
            case CANCELLED:
                applicationDto.getPet().setActive(false);
                petService.savePet(mapToPetDto(applicationDto.getPet()));
                mailService.sendEmail(application.getUser().getEmail(), "Application #" + applicationDto.getId() + " Cancelled", "We regret to inform that your application for pet " + applicationDto.getPet().getName() + " is cancelled.");
                break;
        }
        application.setPet(applicationDto.getPet());
        applicationService.updateApplication(application);


        return "redirect:/admin/applications";
    }
}