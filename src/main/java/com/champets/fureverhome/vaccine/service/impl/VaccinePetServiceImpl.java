package com.champets.fureverhome.vaccine.service.impl;

import com.champets.fureverhome.exception.pet.PetNotFoundException;
import com.champets.fureverhome.exception.vaccine.VaccineListEmptyException;
import com.champets.fureverhome.exception.vaccine.VaccineNotFoundException;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.model.dto.VaccinePetDto;
import com.champets.fureverhome.vaccine.repository.VaccinePetRepository;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import com.champets.fureverhome.vaccine.service.VaccinePetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.champets.fureverhome.vaccine.model.mapper.VaccinePetMapper.mapToVaccinePet;
import static com.champets.fureverhome.vaccine.model.mapper.VaccinePetMapper.mapToVaccinePetDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccinePetServiceImpl implements VaccinePetService {
    @Autowired
    private VaccinePetRepository vaccinePetRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

//    @Override
//    public VaccinePet saveVaccinePet(VaccinePetDto vaccinePetDto, Long petId, Long vaccineId) {
//        Pet pet = petRepository.findById(petId).get();
//        Vaccine vaccine = vaccineRepository.findById(vaccineId).get();
//        VaccinePet vaccinePet = mapToVaccinePet(vaccinePetDto);
//        return vaccinePet;
//    }
    @Override
    public VaccinePet saveVaccinePet(VaccinePetDto vaccinePetDto, Long petId, Long vaccineId) {
        try {
            Pet pet = petRepository.findById(petId)
                    .orElseThrow(() -> new PetNotFoundException("Pet with ID " + petId + " not found."));

            Vaccine vaccine = vaccineRepository.findById(vaccineId)
                    .orElseThrow(() -> new VaccineNotFoundException("Vaccine with ID " + vaccineId + " not found."));

            VaccinePet vaccinePet = mapToVaccinePet(vaccinePetDto);
            vaccinePet.setPet(pet);
            vaccinePet.setVaccine(vaccine);
            vaccinePetRepository.save(vaccinePet);
            return vaccinePet;
        } catch (PetNotFoundException | VaccineNotFoundException e) {
            if(e instanceof PetNotFoundException){
                throw new PetNotFoundException("Pet with ID " + petId + " not found.");
            } else {
                throw new VaccineNotFoundException("Vaccine with ID " + vaccineId + " not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the VaccinePet.", e);
        }
    }


    @Override
    public List<VaccinePetDto> findVaccineListByPetId(Long petId) {
        List<VaccinePet> vaccinePetList = vaccinePetRepository.findVaccinesByPetId(petId);
        return vaccinePetList.stream().map(vaccinePet -> mapToVaccinePetDto(vaccinePet)).collect(Collectors.toList());
    }

    @Override
    public List<VaccinePetDto> findPetsWithVaccine(Long vaccineId) {
        List<VaccinePet> vaccinePetList = vaccinePetRepository.findPetsWithVaccineId(vaccineId);
        return vaccinePetList.stream().map(vaccinePet -> mapToVaccinePetDto(vaccinePet)).collect(Collectors.toList());
    }

    @Override
    public void updateVaccinePet(VaccinePetDto vaccinePetDto) {
        VaccinePet vaccinePet = mapToVaccinePet(vaccinePetDto);
        vaccinePetRepository.save(vaccinePet);
    }
}
