package com.champets.fureverhome.application.service.impl;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.repository.ApplicationRepository;
import com.champets.fureverhome.application.service.ApplicationService;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.model.UserRole;
import com.champets.fureverhome.user.repository.UserRepository;
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
    public Application saveApplication(ApplicationDto applicationDto, Long petId, Long userId) {
        Pet pet = petRepository.findById(petId).get();
        UserEntity user = userRepository.findById(userId).get(); //null pointer exception
        Application application = mapToApplication(applicationDto);
        application.setPet(pet);
        application.setUser(user);
        application.setApplicationStatus(ApplicationStatus.PENDING);
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
    public List<ApplicationDto> searchApplicationsByEmailAddress(String emailAddress) {
//        List<Application> applications = applicationRepository.searchApplicationsByEmailAddress(emailAddress);
//        return applications.stream().map(application -> mapToApplicationDto(application)).collect(Collectors.toList());
        return null;
    }
}