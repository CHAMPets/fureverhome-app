package com.champets.fureverhome.pet.service;

import com.champets.fureverhome.pet.model.Pet;

import java.util.List;

public interface PetService {
    List<Pet> findAllPets();
    Pet savePet(Pet pet);
}
