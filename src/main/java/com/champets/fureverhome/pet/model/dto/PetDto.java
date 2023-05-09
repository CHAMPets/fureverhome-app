package com.champets.fureverhome.pet.model.dto;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PetDto {

    @NotNull
    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private BodySize bodySize;
    private LocalDate rescueDate;
    private String imagePath;
    private String description;
    private Boolean isSterilized;
    private Integer applicationLimit;
    private Integer applicationCounter;
    private LocalDate lastDateModified;
    private String createdBy;
    private String lastModifiedBy;
    private LocalDate createdDate;

}
