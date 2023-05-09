package com.champets.fureverhome.application.service.impl;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.repository.ApplicationRepository;
import com.champets.fureverhome.application.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.champets.fureverhome.application.model.mapper.ApplicationMapper.mapToApplication;
import static com.champets.fureverhome.application.model.mapper.ApplicationMapper.mapToApplicationDto;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public List<Application> findAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream().collect(Collectors.toList());
    }

    @Override
    public Application saveApplication(ApplicationDto applicationDto) {
        Application application = mapToApplication(applicationDto);
        return applicationRepository.save(application);
    }

    @Override
    public ApplicationDto findApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).get();
        return mapToApplicationDto(application);
    }

    @Override
    public void updateApplication(ApplicationDto applicationDto) {
        Application application = mapToApplication(applicationDto);
        applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    @Override
    public List<ApplicationDto> searchApplicationsByEmailAddress(String emailAddress) {
        List<Application> applications = applicationRepository.searchApplicationsByEmailAddress(emailAddress);
        return applications.stream().map(application -> mapToApplicationDto(application)).collect(Collectors.toList());
    }
}