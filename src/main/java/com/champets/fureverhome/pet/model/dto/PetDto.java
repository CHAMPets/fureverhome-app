package com.champets.fureverhome.pet.model.dto;

import com.champets.fureverhome.application.model.Application;
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
import javax.persistence.Transient;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PetDto {

    private Long id;

    @NotEmpty(message = "Enter name")
    private String name;

    @NotNull(message = "Enter age")
    @Min(value = 0, message = "Age must be greater than 0")
    @Max(value = 20, message = "Age must be less than or equal to 20")
    private Integer age;

    @NotNull(message = "Select a gender")
    private Gender gender;

    @NotNull(message = "Select a body size")
    private BodySize bodySize;

    @PastOrPresent(message = "Rescue date must be today or a past date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rescueDate;

    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Select a type")
    private Type type;

    private Boolean isSterilized;
    private Boolean active;
    private Integer applicationLimit;
    private Integer applicationCounter;

    private LocalDate lastDateModified;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createdBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String lastModifiedBy;

    private LocalDate createdDate;

    private List<VaccinePet> vaccineList;
    private List<Application> applications;

}
