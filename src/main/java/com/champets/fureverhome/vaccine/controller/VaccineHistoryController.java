package com.champets.fureverhome.vaccine.controller;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccineHistory;
import com.champets.fureverhome.vaccine.repository.VaccineHistoryRepository;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vaccine-history")
public class VaccineHistoryController {

    private VaccineRepository vaccineRepository;
    private VaccineHistoryRepository vaccineHistoryRepository;

    public VaccineHistoryController(VaccineRepository vaccineRepository, VaccineHistoryRepository vaccineHistoryRepository){
        this.vaccineRepository = vaccineRepository;
        this.vaccineHistoryRepository = vaccineHistoryRepository;
    }

    @PostMapping
    public VaccineHistory saveHistoryWithVaccine(@RequestBody VaccineHistory vaccineHistory){
        return vaccineHistoryRepository.save(vaccineHistory);
    }

    @GetMapping("/all-vaccine-histories")
    @ResponseBody
    public List<VaccineHistory> findAllVaccineHistories(){
        return vaccineHistoryRepository.findAll();
    }
    @GetMapping("/all-vaccines")
    @ResponseBody
    public List<Vaccine> findAllVaccines(){
        return vaccineRepository.findAll();
    }
    @GetMapping("/{vaccineHistoryId}")
    public Optional<VaccineHistory> findVaccineHistory(@PathVariable Long id) {
        return vaccineHistoryRepository.findById(id);
    }
    @GetMapping("/vaccine/{vaccineId}")
    public Optional<Vaccine> findVaccine(@PathVariable Long id) {
        return vaccineRepository.findById(id);
    }
}
