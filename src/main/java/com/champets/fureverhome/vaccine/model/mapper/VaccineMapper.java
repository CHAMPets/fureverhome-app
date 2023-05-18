package com.champets.fureverhome.vaccine.model.mapper;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.dto.VaccineDto;

public class VaccineMapper {
    public static Vaccine mapToVaccine(VaccineDto vaccineDto) {
        Vaccine vaccine = Vaccine.builder()
                .id(vaccineDto.getId())
                .type(vaccineDto.getType())
                .name(vaccineDto.getName())
                .description(vaccineDto.getDescription())
                .build();
        return vaccine;
    }

    public static VaccineDto mapToVaccineDto(Vaccine vaccine) {
        VaccineDto vaccineDto = VaccineDto.builder()
                .id(vaccine.getId())
                .type(vaccine.getType())
                .name(vaccine.getName())
                .description(vaccine.getDescription())
                .build();
        return vaccineDto;
    }
}
