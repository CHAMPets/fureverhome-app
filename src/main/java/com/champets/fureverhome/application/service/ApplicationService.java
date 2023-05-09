package com.champets.fureverhome.application.service;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {
    List<Application> findAllApplications();

    Application saveApplication(ApplicationDto applicationDto);

    ApplicationDto findApplicationById(Long applicationId);

    void updateApplication(ApplicationDto petDto);

    void deleteApplication(Long applicationId);

    List<ApplicationDto> searchApplicationsByEmailAddress(String emailAddress);
}
