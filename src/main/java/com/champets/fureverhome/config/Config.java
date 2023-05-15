package com.champets.fureverhome.config;

import com.champets.fureverhome.user.enums.RoleName;
import com.champets.fureverhome.user.model.Role;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.repository.RoleRepository;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import com.champets.fureverhome.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(VaccineRepository vaccineRepository, RoleRepository userRoleRepository) {
        return args -> {
            if (vaccineRepository.findAll().isEmpty()) {
                Vaccine fiveInOneVaccine = new Vaccine("5-in-1 vaccine", "CPV-DHLP", "The CPV-DHLP vaccine provides immunity against distemper, adenovirus (hepatitis), parainfluenza, and parvovirus.");
                Vaccine rabbiesVaccine = new Vaccine("Rabies vaccine", "Rabies", "Rabies vaccines contain inactivated pieces of the rabies virus, meaning the vaccine will not cause a pet to develop rabies. Once injected into the pet, the immune system reacts to the foreign rabies virus material by developing antibodies against the virus.");

                Vaccine threeInOneVaccine = new Vaccine("3-in-1 vaccine", "FVRCP", "The FVRCP vaccine effectively protects your kitty companion from three highly contagious and life-threatening feline diseases: Feline Viral Rhinotracheitis, Feline Calicivirus, and Feline Panleukopenia.");

                ArrayList<Vaccine> vaccines = new ArrayList<>();
                vaccines.add(fiveInOneVaccine);
                vaccines.add(rabbiesVaccine);
                vaccines.add(threeInOneVaccine);

                vaccineRepository.saveAll(vaccines);


            }

            if (userRoleRepository.findAll().isEmpty()) {

                Role rootAdminRole = new Role(1L, "ROOT");
                Role adminRole = new Role(2L, "ADMIN");
                Role userRole = new Role(3L, "USER");


                ArrayList<Role> roles = new ArrayList<>();
                roles.add(rootAdminRole);
                roles.add(adminRole);
                roles.add(userRole);

                userRoleRepository.saveAll(roles);
            }

        };

    }

}
