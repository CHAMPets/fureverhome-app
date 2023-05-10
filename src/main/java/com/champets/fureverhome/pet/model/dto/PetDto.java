package com.champets.fureverhome.pet.model.dto;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class PetDto {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    private Integer age;

    @NotEmpty(message = "Gender should not be empty")
    private Gender gender;

    private BodySize bodySize;

    @NotEmpty(message = "Rescue date should not be empty")
    private LocalDate rescueDate;

    @NotEmpty(message = "Image path should not be empty")
    private String imagePath;

    @NotEmpty(message = "Description should not be empty")
    private String description;
    private Type type;
    private Boolean isSterilized;
    private Boolean isActive;
    private Integer applicationLimit;
    private Integer applicationCounter;
    private LocalDate lastDateModified;
    private String createdBy;
    private String lastModifiedBy;
    private LocalDate createdDate;

}
