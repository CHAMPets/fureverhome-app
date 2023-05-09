package com.champets.fureverhome.pet.service.impl;

import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<PetDto> findAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
    }
    @Override
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet findPetById(long petId) {
        Pet pet = petRepository.findById(petId).get();
        return pet;
    }

    @Override
    public void updatePet(Pet pet) {
        petRepository.save(pet);
    }
    private PetDto mapToPetDto(Pet pet){
        PetDto petDto = PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .age(pet.getAge())
                .gender(pet.getGender())
                .bodySize(pet.getBodySize())
                .description(pet.getDescription())
                .applicationCounter(pet.getApplicationCounter())
                .imagePath(pet.getImagePath())
                .createdDate(pet.getCreatedDate())
                .isSterilized(pet.getIsSterilized())
                .rescueDate(pet.getRescueDate())
                .applicationLimit(pet.getApplicationLimit())
                .createdBy(pet.getCreatedBy())
                .lastDateModified(pet.getLastDateModified())
                .lastModifiedBy(pet.getLastModifiedBy())
                .build();
        return petDto;
    }

}
