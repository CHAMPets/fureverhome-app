package com.champets.fureverhome.user.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class UserDto {
    private Long id;

    private String emailAddress;

    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private Long roleId;

    private LocalDate lastDateModified;

    private String createdBy;

    private String lastModifiedBy;

    private LocalDate createdDate;
}