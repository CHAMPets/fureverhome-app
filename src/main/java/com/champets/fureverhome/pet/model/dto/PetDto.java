package com.champets.fureverhome.pet.model.dto;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PetDto {

    private Long id;

    @NotEmpty(message = "Enter name")
    private String name;

    @NotNull(message = "Enter age")
    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @NotNull(message = "Select a gender")
    private Gender gender;

    @NotNull(message = "Select a body size")
    private BodySize bodySize;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rescueDate;

    @NotEmpty(message = "Image path should not be empty")
    private String imagePath;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Select a type")
    private Type type;

    private Boolean isSterilized;
    private Boolean active;
    private Integer applicationLimit;
    private Integer applicationCounter;

    @UpdateTimestamp
    private LocalDate lastDateModified;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String lastModifiedBy;

    @CreationTimestamp
    private LocalDate createdDate;

    private List<Vaccine> vaccines;
    private List<VaccinePet> vaccineList;

}
