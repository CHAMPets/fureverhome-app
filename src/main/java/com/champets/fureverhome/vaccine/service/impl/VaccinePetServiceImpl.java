package com.champets.fureverhome.vaccine.service.impl;

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


    @Override
    public VaccinePet saveVaccinePet(VaccinePetDto vaccinePetDto, Long petId, Long vaccineId) {
        Pet pet = petRepository.findById(petId).get();
        Vaccine vaccine = vaccineRepository.findById(vaccineId).get();
        VaccinePet vaccinePet = mapToVaccinePet(vaccinePetDto);
        return vaccinePet;
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
