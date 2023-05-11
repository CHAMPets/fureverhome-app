package com.champets.fureverhome.vaccine.controller;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.repository.VaccinePetRepository;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vaccine-history")
public class VaccineHistoryController {

    private VaccineRepository vaccineRepository;
    private VaccinePetRepository vaccinePetRepository;

    public VaccineHistoryController(VaccineRepository vaccineRepository, VaccinePetRepository vaccinePetRepository){
        this.vaccineRepository = vaccineRepository;
        this.vaccinePetRepository = vaccinePetRepository;
    }

    @PostMapping
    public VaccinePet saveHistoryWithVaccine(@RequestBody VaccinePet vaccinePet){
        return vaccinePetRepository.save(vaccinePet);
    }

    @GetMapping("/all-vaccine-histories")
    @ResponseBody
    public List<VaccinePet> findAllVaccineHistories(){
        return vaccinePetRepository.findAll();
    }
    @GetMapping("/all-vaccines")
    @ResponseBody
    public List<Vaccine> findAllVaccines(){
        return vaccineRepository.findAll();
    }
    @GetMapping("/{vaccineHistoryId}")
    public Optional<VaccinePet> findVaccineHistory(@PathVariable Long id) {
        return vaccinePetRepository.findById(id);
    }
    @GetMapping("/vaccine/{vaccineId}")
    public Optional<Vaccine> findVaccine(@PathVariable Long id) {
        return vaccineRepository.findById(id);
    }
}
