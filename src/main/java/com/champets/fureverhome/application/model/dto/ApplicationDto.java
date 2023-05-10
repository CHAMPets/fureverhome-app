package com.champets.fureverhome.application.model.dto;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.user.model.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
public class ApplicationDto {
    private Long id;
    private Pet pet;
    private User user;
    private ApplicationStatus applicationStatus;
    private LocalDate releaseDate;
}