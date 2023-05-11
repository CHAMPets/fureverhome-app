package com.champets.fureverhome.pet.service;

import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;

import java.util.List;

public interface PetService {

    List<PetDto> findAllPets();

    List<PetDto> findAllActivePets();
    Pet savePet(Pet pet);

    PetDto findPetById(long petId);

    void updatePet(PetDto pet);
}
