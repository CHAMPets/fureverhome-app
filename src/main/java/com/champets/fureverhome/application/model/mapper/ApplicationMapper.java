package com.champets.fureverhome.application.model.mapper;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;

public class ApplicationMapper {
    public static Application mapToApplication(ApplicationDto applicationDto) {
        Application application = Application.builder()
                .id(applicationDto.getId())
                .pet(applicationDto.getPet())
                .user(applicationDto.getUser())
                .applicationStatus(applicationDto.getApplicationStatus())
                .releaseDate(applicationDto.getReleaseDate())
                .build();
        return application;
    }
    public static ApplicationDto mapToApplicationDto(Application application) {
        ApplicationDto applicationDto = ApplicationDto.builder()
                .id(application.getId())
                .pet(application.getPet())
                .user(application.getUser())
                .applicationStatus(application.getApplicationStatus())
                .releaseDate(application.getReleaseDate())
                .build();
        return applicationDto;
    }
}