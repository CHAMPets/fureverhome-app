package com.champets.fureverhome.config;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class Config {
    @Bean
    CommandLineRunner commandLineRunner(VaccineRepository vaccineRepository){
        return args -> {
            if(vaccineRepository.findAll().isEmpty()){
                Vaccine fiveInOneVaccine = new Vaccine("5-in-1 vaccine", "CPV-DHLP", "The CPV-DHLP vaccine provides immunity against distemper, adenovirus (hepatitis), parainfluenza, and parvovirus.");
                Vaccine rabbiesVaccine = new Vaccine("Rabbies vaccine", "Rabbies", "Rabies vaccines contain inactivated pieces of the rabies virus, meaning the vaccine will not cause a pet to develop rabies. Once injected into the pet, the immune system reacts to the foreign rabies virus material by developing antibodies against the virus.");

                Vaccine threeInOneVaccine = new Vaccine("3-in-1 vaccine", "FVRCP", "The FVRCP vaccine effectively protects your kitty companion from three highly contagious and life-threatening feline diseases: Feline Viral Rhinotracheitis, Feline Calicivirus, and Feline Panleukopenia.");

                ArrayList<Vaccine> vaccines = new ArrayList<>();
                vaccines.add(fiveInOneVaccine);
                vaccines.add(rabbiesVaccine);
                vaccines.add(threeInOneVaccine);

                vaccineRepository.saveAll(vaccines);
            }
        };
    }
}
