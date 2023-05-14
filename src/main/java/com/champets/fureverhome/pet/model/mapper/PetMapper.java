package com.champets.fureverhome.pet.model.mapper;

import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;

public class PetMapper {

    public static Pet mapToPet(PetDto pet) {
        Pet petDto = Pet.builder()
                .id(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .type(pet.getType())
                .gender(pet.getGender())
                .bodySize(pet.getBodySize())
                .description(pet.getDescription())
                .applicationCounter(pet.getApplicationCounter())
                .imagePath(pet.getImagePath())
                .createdDate(pet.getCreatedDate())
                .isSterilized(pet.getIsSterilized())
                .active(pet.getActive())
                .rescueDate(pet.getRescueDate())
                .applicationLimit(pet.getApplicationLimit())
                .createdBy(pet.getCreatedBy())
                .lastDateModified(pet.getLastDateModified())
                .lastModifiedBy(pet.getLastModifiedBy())
                .vaccineList(pet.getVaccineList())
                .applications(pet.getApplications())
                .build();
        return petDto;
    }

    public static PetDto mapToPetDto(Pet pet){
        PetDto petDto = PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .type(pet.getType())
                .gender(pet.getGender())
                .bodySize(pet.getBodySize())
                .description(pet.getDescription())
                .applicationCounter(pet.getApplicationCounter())
                .imagePath(pet.getImagePath())
                .createdDate(pet.getCreatedDate())
                .isSterilized(pet.getIsSterilized())
                .active(pet.getActive())
                .rescueDate(pet.getRescueDate())
                .applicationLimit(pet.getApplicationLimit())
                .createdBy(pet.getCreatedBy())
                .lastDateModified(pet.getLastDateModified())
                .lastModifiedBy(pet.getLastModifiedBy())
                .vaccineList(pet.getVaccineList())
                .applications(pet.getApplications())
                .build();
        return petDto;
    }
}
