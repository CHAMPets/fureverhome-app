package com.champets.fureverhome.vaccine.service;

import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.model.dto.VaccineDto;
import com.champets.fureverhome.vaccine.model.dto.VaccinePetDto;
import java.util.List;

public interface VaccinePetService {
    VaccinePet saveVaccinePet(VaccinePetDto vaccinePetDto, Long petId, Long vaccineId);
    List<VaccinePetDto> findVaccineListByPetId(Long petId);

    List<VaccinePetDto> findPetsWithVaccine(Long vaccineId);

    void updateVaccinePet(VaccinePetDto vaccinePetDto);

}
