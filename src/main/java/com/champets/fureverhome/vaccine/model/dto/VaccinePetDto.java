package com.champets.fureverhome.vaccine.model.dto;

import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.vaccine.model.Vaccine;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VaccinePetDto {
    private Long id;
    private Vaccine vaccine;
    private Pet pet;
}
