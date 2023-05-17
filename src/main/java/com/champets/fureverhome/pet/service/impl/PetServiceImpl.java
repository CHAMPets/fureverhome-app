package com.champets.fureverhome.pet.service.impl;

import com.champets.fureverhome.exception.pet.PetNotFoundException;
import com.champets.fureverhome.exception.pet.PetServiceException;
import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.pet.model.mapper.PetMapper;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.pet.service.PetService;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.repository.VaccinePetRepository;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPet;
import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPetDto;

@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinePetRepository vaccinePetRepository;
    private EntityManager entityManager;

    @Autowired
    public PetServiceImpl(PetRepository petRepository,
                          VaccineRepository vaccineRepository,
                          VaccinePetRepository vaccinePetRepository,
                          EntityManager entityManager) {
        this.petRepository = petRepository;
        this.vaccineRepository = vaccineRepository;
        this.vaccinePetRepository = vaccinePetRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<PetDto> findAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
    }

    @Override
    public List<PetDto> findAllActivePets() {
        List<Pet> pets = petRepository.findByActiveTrue();
        return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
    }

    @Override
    public List<PetDto> findPetsByFilter(Type type, BodySize size, Gender gender) {
        List<Pet> pets = petRepository.findPetsByFilter(type, size, gender);
        return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
    }

    @Override
    public List<PetDto> findActivePetsNotAppliedByUser(Long userId) {
        List<Pet> pets = petRepository.findPetsNotAppliedByUser(userId);
        return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
    }

    @Override
    public List<PetDto> findActivePetsNotAppliedByUserWithFilter(Long userId, Type type, BodySize size, Gender gender) {
        List<Pet> pets = petRepository.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender);
        return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
    }

    @Override
    public Pet savePet(PetDto petDto) {
        try {
            Pet pet = mapToPet(petDto);
            return petRepository.save(pet);
        } catch (Exception e) {
            throw new PetServiceException("Failed to save pet: " + petDto.getId(), e);
        }
    }

    @Override
    public PetDto findPetById(long petId) {
        Pet pet = petRepository.findById(petId).get();
        return mapToPetDto(pet);
    }

    @Override
    public void updatePet(PetDto petDto) {
        Pet pet = mapToPet(petDto);


        List<VaccinePet> vaccineHistory = new ArrayList<>();
        for (VaccinePet vaccinePet: petDto.getVaccineList()) {
            vaccineHistory.add(vaccinePet);
        }

        pet.setVaccineList(vaccineHistory);
        petRepository.save(pet);
    }

    @Transactional
    @Override
    public void deletePetVaccinesByPetId(Long petId) {
        try {
            Query query = entityManager.createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId");
            query.setParameter("petId", petId);
            query.executeUpdate();
        } catch (Exception e) {
            throw new PetServiceException("Failed to delete pet vaccines: " + petId, e);
        }
    }

//        Pet savedPet = petRepository.save(pet);
//        List<VaccinePet> existingVaccines = savedPet.getVaccineList();
//        List<VaccinePet> newVaccines = new ArrayList<>();
//        for (VaccinePet vaccinePet : petDto.getVaccineList()) {
//            Vaccine vaccine = vaccinePet.getVaccine();
//            VaccinePet newVaccinePet = new VaccinePet(savedPet, vaccine);
//            newVaccines.add(newVaccinePet);
//        }
//        existingVaccines.clear();
//        existingVaccines.addAll(newVaccines);
//        petRepository.save(savedPet);
}
