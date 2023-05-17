package com.champets.fureverhome.application.service;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    List<ApplicationDto> findAllApplications();

    Application saveApplication(ApplicationDto applicationDto, Long petId, Long userId);

    ApplicationDto findApplicationById(Long applicationId);

    List<ApplicationDto> findApplicationsByPetId(Long petId);

    List<ApplicationDto> findApplicationsByUserId(Long userId);

    Optional<Application> findApplicationByPetIdAndUserId(Long petId, Long userId);

    void updateApplication(ApplicationDto applicationDto);

}
