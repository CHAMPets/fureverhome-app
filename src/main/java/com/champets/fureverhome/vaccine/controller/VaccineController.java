package com.champets.fureverhome.vaccine.controller;
import com.champets.fureverhome.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VaccineController {

    private final VaccineService vaccineService;

    @Autowired
    public VaccineController(VaccineService vaccineService){
        this.vaccineService = vaccineService;
    }

}
