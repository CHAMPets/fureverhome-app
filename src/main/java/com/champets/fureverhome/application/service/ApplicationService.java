package com.champets.fureverhome.application.service;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {
    List<ApplicationDto> findAllApplications();

    Application saveApplication(ApplicationDto applicationDto, Long petId, Long userId);

    ApplicationDto findApplicationById(Long applicationId);

    void updateApplication(ApplicationDto applicationDto);

    List<ApplicationDto> searchApplicationsByEmailAddress(String emailAddress);
}
