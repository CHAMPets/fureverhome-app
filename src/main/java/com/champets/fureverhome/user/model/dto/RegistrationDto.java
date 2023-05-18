package com.champets.fureverhome.user.model.dto;

import com.champets.fureverhome.application.model.Application;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private List<Application> applications = new ArrayList<>();
}
