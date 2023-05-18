package com.champets.fureverhome.vaccine.model.mapper;

import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.model.dto.VaccinePetDto;

public class VaccinePetMapper {
    public static VaccinePet mapToVaccinePet(VaccinePetDto vaccinePetDto) {
        VaccinePet vaccinePet = VaccinePet.builder()
                .id(vaccinePetDto.getId())
                .pet(vaccinePetDto.getPet())
                .vaccine(vaccinePetDto.getVaccine())
                .build();
        return vaccinePet;
    }

    public static VaccinePetDto mapToVaccinePetDto(VaccinePet vaccinePet) {
        VaccinePetDto vaccinePetDto = VaccinePetDto.builder()
                .id(vaccinePet.getId())
                .pet(vaccinePet.getPet())
                .vaccine(vaccinePet.getVaccine())
                .build();

        return vaccinePetDto;
    }
}
