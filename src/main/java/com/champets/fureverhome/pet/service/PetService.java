package com.champets.fureverhome.pet.service;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;

import java.util.List;

public interface PetService {

    List<PetDto> findAllPets();

    List<PetDto> findAllActivePets();
    List<PetDto> findPetsByFilter(Type type, BodySize size, Gender gender);

    List<PetDto> findActivePetsNotAppliedByUser(Long userId);

    List<PetDto> findActivePetsNotAppliedByUserWithFilter(Long userId, Type type, BodySize size, Gender gender);

    Pet savePet(PetDto petDto);

    PetDto findPetById(long petId);

    void updatePet(PetDto pet);

    void deletePetVaccinesByPetId(Long petId);
}
