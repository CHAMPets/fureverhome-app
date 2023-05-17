package com.champets.fureverhome.user.model.dto;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.user.model.Role;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    private String username;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private LocalDate createdDate;

    private List<Application> applications = new ArrayList<>();
}