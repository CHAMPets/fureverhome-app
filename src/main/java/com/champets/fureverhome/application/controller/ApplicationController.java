package com.champets.fureverhome.application.controller;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/applications")
    public String listApplications(Model model) {
        List<ApplicationDto> applications = applicationService.findAllApplications();
        model.addAttribute("applications", applications);
        return "application-list";
    }

    @GetMapping("/applications/{applicationId}")
    public String applicationDetail(@PathVariable("applicationId") Long applicationId, Model model) {
        ApplicationDto application = applicationService.findApplicationById(applicationId);
        model.addAttribute("application", application);
        return "application-detail";
    }

    @GetMapping("/applications/new")
    public String createApplicationForm(Model model) {
        Application application = new Application();
        model.addAttribute("application", application);
        return "application-create";
    }

    @PostMapping("/applications/new")
    public String saveApplication(@ModelAttribute("application") ApplicationDto applicationDto, Model model) {
        applicationService.saveApplication(applicationDto);
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
                                    @ModelAttribute("application") ApplicationDto application,
                                    BindingResult result, Model model) {
        application.setId(applicationId);
        applicationService.updateApplication(application);
        return "redirect:/applications";
    }
}