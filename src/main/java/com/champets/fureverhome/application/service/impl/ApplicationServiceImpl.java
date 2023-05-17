package com.champets.fureverhome.application.service.impl;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.repository.ApplicationRepository;
import com.champets.fureverhome.application.service.ApplicationService;
import com.champets.fureverhome.exception.application.ApplicationNotFoundException;
import com.champets.fureverhome.exception.pet.PetNotFoundException;
import com.champets.fureverhome.exception.user.UserNotFoundException;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.champets.fureverhome.application.model.mapper.ApplicationMapper.mapToApplication;
import static com.champets.fureverhome.application.model.mapper.ApplicationMapper.mapToApplicationDto;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ApplicationDto> findAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream().map(application -> mapToApplicationDto(application)).collect(Collectors.toList());
    }
    @Override
    public List<ApplicationDto> findApplicationsByPetId(Long petId) {
        List<Application> applications = applicationRepository.findApplicationsByPetId(petId);
        return applications.stream().map(application -> mapToApplicationDto(application)).collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDto> findApplicationsByUserId(Long userId) {
        List<Application> applications = applicationRepository.findApplicationsByUserId(userId);
        return applications.stream().map(application -> mapToApplicationDto(application)).collect(Collectors.toList());
    }

    @Override
    public Optional<Application> findApplicationByPetIdAndUserId(Long petId, Long userId) {
        Optional<Application> application = applicationRepository.findApplicationByPetIdAndUserId(petId, userId);
        return application;
    }

    @Override
    public Application saveApplication(ApplicationDto applicationDto, Long petId, Long userId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet not found with ID: " + petId));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        Application application = mapToApplication(applicationDto);
        application.setPet(pet);
        application.setUser(user);
        application.setApplicationStatus(ApplicationStatus.PENDING);
        return applicationRepository.save(application);
    }

    @Override
    public ApplicationDto findApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + applicationId));
        return mapToApplicationDto(application);
    }

    @Override
    public void updateApplication(ApplicationDto applicationDto) {
        Application application = mapToApplication(applicationDto);
        if (application == null) {
            throw new ApplicationNotFoundException("Application not found with ID: " + application.getId());
        }
        applicationRepository.save(application);
    }

}