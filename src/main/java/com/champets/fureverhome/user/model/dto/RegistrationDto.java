package com.champets.fureverhome.user.model.dto;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.user.model.UserRole;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class RegistrationDto {

    private Long id;

    private String email;

    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    //private List<Application> applications = new ArrayList<>();
}
