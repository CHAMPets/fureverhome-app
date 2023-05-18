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
        try {
            List<Pet> pets = petRepository.findAll();
            return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PetServiceException("Failed to retrieve all pets", e);
        }
    }

    @Override
    public List<PetDto> findAllActivePets() {
        try {
            List<Pet> pets = petRepository.findByActiveTrue();
            return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PetServiceException("Failed to retrieve all active pets", e);
        }
    }

    @Override
    public List<PetDto> findPetsByFilter(Type type, BodySize size, Gender gender) {
        try {
            List<Pet> pets = petRepository.findPetsByFilter(type, size, gender);
            return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PetServiceException("Failed to retrieve all pets by filter", e);
        }
    }

    @Override
    public List<PetDto> findActivePetsNotAppliedByUser(Long userId) {
        try {
            List<Pet> pets = petRepository.findPetsNotAppliedByUser(userId);
            return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PetServiceException("Failed to retrieve all pets not applied by user", e);
        }
    }

    @Override
    public List<PetDto> findActivePetsNotAppliedByUserWithFilter(Long userId, Type type, BodySize size, Gender gender) {
        try {
            List<Pet> pets = petRepository.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender);
            return pets.stream().map((pet) -> mapToPetDto(pet)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new PetServiceException("Failed to retrieve all pets not applied by user wiht filter", e);
        }
    }

    @Override
    public Pet savePet(PetDto petDto) {

            Pet pet = mapToPet(petDto);
            return petRepository.save(pet);
    }

    @Override
    public PetDto findPetById(long petId) {
        try {
            Pet pet = petRepository.findById(petId)
                    .orElseThrow(() -> new PetNotFoundException("Pet not found: " + petId));
            return mapToPetDto(pet);
        } catch (PetNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PetServiceException("Failed to find pet: " + petId, e);
        }
    }

    @Override
    public void updatePet(PetDto petDto) {
        try {
            Pet pet = mapToPet(petDto);
            List<VaccinePet> vaccineHistory = new ArrayList<>();
            for (VaccinePet vaccinePet : petDto.getVaccineList()) {
                vaccineHistory.add(vaccinePet);
            }
            pet.setVaccineList(vaccineHistory);
            petRepository.save(pet);
        } catch (Exception e) {
            throw new PetServiceException("Failed to update pet: " + petDto.getId(), e);
        }
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
}
