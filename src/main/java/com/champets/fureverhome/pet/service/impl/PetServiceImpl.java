package com.champets.fureverhome.pet.service.impl;

import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class PetServiceImpl implements PetService {
    @Autowired
    private PetRepository petRepository;
    @Override
    public List<Pet> findAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().collect(Collectors.toList());
    }
    @Override
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }
}
