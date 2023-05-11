package com.champets.fureverhome.vaccine.service;

import com.champets.fureverhome.vaccine.model.Vaccine;

import java.util.List;

public interface VaccineService {

    List<Vaccine> findAllVaccines();

    Vaccine findVaccineById(Long vaccineId);
}
