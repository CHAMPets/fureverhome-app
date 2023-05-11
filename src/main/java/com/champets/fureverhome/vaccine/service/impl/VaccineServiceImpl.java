package com.champets.fureverhome.vaccine.service.impl;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import com.champets.fureverhome.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineServiceImpl implements VaccineService {
    private final VaccineRepository vaccineRepository;

    @Autowired
    public VaccineServiceImpl(VaccineRepository vaccineRepository){
        this.vaccineRepository = vaccineRepository;
    }
    @Override
    public List<Vaccine> findAllVaccines() {
        List<Vaccine> vaccines = vaccineRepository.findAll();
        return vaccines.stream().collect(Collectors.toList());
    }

    @Override
    public Vaccine findVaccineById(Long vaccineId) {
        Vaccine vaccine = vaccineRepository.findById(vaccineId).get();
        return vaccine;
    }
}
