package com.champets.fureverhome.pet.controller;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.pet.model.mapper.PetMapper;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.service.UserService;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.model.dto.VaccinePetDto;
import com.champets.fureverhome.vaccine.service.VaccinePetService;
import com.champets.fureverhome.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import com.champets.fureverhome.pet.service.PetService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.champets.fureverhome.pet.model.Pet;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPet;

@Controller
public class PetController {
    private final PetService petService;
    private final VaccineService vaccineService;
    private final VaccinePetService vaccinePetService;
    private final UserService userService;

    @Autowired
    public PetController(PetService petService, VaccineService vaccineService, VaccinePetService vaccinePetService, UserService userService) {

        this.petService = petService;
        this.vaccineService = vaccineService;
        this.vaccinePetService = vaccinePetService;
        this.userService = userService;
    }

    private List<VaccinePet> createVaccineHistory(List<Long> vaccineIds, PetDto petDto) {
        List<VaccinePet> vaccineHistory = new ArrayList<>();
        if (vaccineIds != null && !vaccineIds.isEmpty()) {
            for (Long vaccineId : vaccineIds) {
                Vaccine vaccine = vaccineService.findVaccineById(vaccineId);
                VaccinePet vaccinePet = new VaccinePet();
                vaccinePet.setPet(mapToPet(petDto));
                vaccinePet.setVaccine(vaccine);
                vaccineHistory.add(vaccinePet);
            }
        }
        return vaccineHistory;
    }

    @GetMapping("/admin")
    public String listPets(Model model){
        UserEntity user = userService.getCurrentUser();
        List<PetDto> pets = petService.findAllPets();
        model.addAttribute("user", user);
        model.addAttribute("pets", pets);
        return "admin/admin-home";
    }

    @GetMapping("/home")
    public String listActivePets(Model model){
        UserEntity user = userService.getCurrentUser();
        List<PetDto> pets = petService.findAllActivePets();
        model.addAttribute("pets", pets);
        model.addAttribute("user", user);
        return "user/user-home";
    }

    @GetMapping("/home/filtered")
    public String listActivePetsByFilterAsUser(   @RequestParam(value="type") String type,
                                            @RequestParam(value="size") String size,
                                            @RequestParam(value= "gender") String gender,
                                            Model model){

        Type enumType = (type != null && !type.equals("ALL")) ? Type.valueOf(type) : null;
        BodySize enumSize = (size != null && !size.equals("ALL")) ? BodySize.valueOf(size) : null;
        Gender enumGender = (gender != null && !gender.equals("ALL")) ? Gender.valueOf(gender) : null;


        List<PetDto> pets = petService.findActivePetsByFilter(enumType, enumSize, enumGender);
        model.addAttribute("pets", pets);
        model.addAttribute("type", type);
        model.addAttribute("size", size);
        model.addAttribute("gender", gender);
        return "user/user-home-filtered";
    }

    @GetMapping("/admin/filtered")
    public String listActivePetsByFilterAsAdmin(   @RequestParam(value="type") String type,
                                            @RequestParam(value="size") String size,
                                            @RequestParam(value= "gender") String gender,
                                            Model model){

        Type enumType = (type != null && !type.equals("ALL")) ? Type.valueOf(type) : null;
        BodySize enumSize = (size != null && !size.equals("ALL")) ? BodySize.valueOf(size) : null;
        Gender enumGender = (gender != null && !gender.equals("ALL")) ? Gender.valueOf(gender) : null;

        List<PetDto> pets = petService.findActivePetsByFilter(enumType, enumSize, enumGender);
        model.addAttribute("pets", pets);
        model.addAttribute("type", type);
        model.addAttribute("size", size);
        model.addAttribute("gender", gender);
        return "admin/admin-home-filtered";
    }

    @GetMapping("/admin/pets/new")
    public String createPetForm(Model model){
        Pet pet = new Pet();
        List<Vaccine> vaccines = vaccineService.findAllVaccines();
        model.addAttribute("vaccines", vaccines);
        model.addAttribute("pet", pet);
        return "admin/pet-create";
    }
    @PostMapping("/admin/pets/new")
    public String savePet(@Valid @ModelAttribute("pet") PetDto petDto,
                          BindingResult result,
                          @RequestParam(name = "vaccineIds", required = false) List<Long> vaccineIds,
                          Model model){

        if(result.hasErrors()){
            List<Vaccine> vaccines = vaccineService.findAllVaccines();
            model.addAttribute("pet", petDto);
            model.addAttribute("vaccines", vaccines);
            return "admin/pet-create";
        }
        List<VaccinePet> vaccineHistory = createVaccineHistory(vaccineIds, petDto);
        Pet pet = mapToPet(petDto);
        pet.setVaccineList(vaccineHistory);
        petService.savePet(petDto);
        return "redirect:/admin";
    }

    //        else {
//            if (!(vaccineIds == null || vaccineIds.isEmpty())) {
//                List<VaccinePet> vaccineHistory = new ArrayList<>();
//                for (Long vaccineId : vaccineIds) {
//                    Vaccine vaccine = vaccineService.findVaccineById(vaccineId);
//                    VaccinePet vaccinePet = new VaccinePet();
//                    vaccinePet.setPet(PetMapper.mapToPet(petDto));
//                    vaccinePet.setVaccine(vaccine);
//                    vaccineHistory.add(vaccinePet);
//                }
//                Pet pet = PetMapper.mapToPet(petDto);
//                pet.setVaccineList(vaccineHistory);
//            }

    @GetMapping("/admin/pets/{petId}/edit")
    public String editPetForm(@PathVariable("petId") Long petId, Model model){
        List<Vaccine> vaccines = vaccineService.findAllVaccines();
        model.addAttribute("vaccine", vaccines);
        PetDto pet = petService.findPetById(petId);
        model.addAttribute("pet", pet);
        return "admin/pet-edit";
    }

    @PostMapping("/admin/pets/{petId}/edit")
    public String updatePet(@PathVariable("petId") Long petId,
                            @Valid @ModelAttribute("pet") PetDto pet,
                            BindingResult result,
                            Model model,
                            @RequestParam(name = "vaccineIds", required = false) List<Long> vaccineIds){

        petService.deletePetVaccinesByPetId(petId);

        if (result.hasErrors()) {
            List<Vaccine> vaccines = vaccineService.findAllVaccines();
            model.addAttribute("vaccine", vaccines);
            model.addAttribute("pet", pet);
            return "admin/pet-edit";
        }
        petService.deletePetVaccinesByPetId(petId);
        List<VaccinePet> vaccineHistory = createVaccineHistory(vaccineIds, pet);
        pet.setVaccineList(vaccineHistory);
        petService.updatePet(pet);
        return "redirect:/admin";
        }

//        pet.setId(petId);
//        List<VaccinePet> vaccineList = new ArrayList<>();
//        for(VaccinePet vaccinePet : vaccineList) {
//            vaccineList.add(vaccinePet);
//            vaccinePet.setPet(PetMapper.mapToPet(pet));
//        }
//              model.addAttribute("vaccine", vaccines);
//              return"redirect:/pets/"+petId+"/edit";
//              List<Vaccine> vaccines = vaccineService.findAllVaccines();

    //        else {
//            if (!(vaccineIds == null || vaccineIds.isEmpty())) {
//                List<VaccinePet> vaccineHistory = new ArrayList<>();
//                for (Long vaccineId : vaccineIds) {
//                    Vaccine vaccine = vaccineService.findVaccineById(vaccineId);
//                    VaccinePet vaccinePet = new VaccinePet();
//                    vaccinePet.setPet(PetMapper.mapToPet(pet));
//                    vaccinePet.setVaccine(vaccine);
//                    vaccineHistory.add(vaccinePet);
//                }
//                pet.setVaccineList(vaccineHistory);
//            } else {
//                List<VaccinePet> vaccineHistory = new ArrayList<>();
//                pet.setVaccineList(vaccineHistory);
//            }

    @GetMapping("/pets/{petId}")
    public String displayPetAsUser(@PathVariable("petId") Long petId, Model model){
        PetDto pet = petService.findPetById(petId);
        List<VaccinePetDto> vaccinePet = vaccinePetService.findVaccineListByPetId(petId);
        model.addAttribute("pet", pet);
        model.addAttribute("vaccines", vaccinePet);
        return "user/user-pet-details";
    }

    @GetMapping("/admin/pets/{petId}")
    public String displayPetAsAdmin(@PathVariable("petId") Long petId, Model model){
        PetDto pet = petService.findPetById(petId);
        List<VaccinePetDto> vaccinePet = vaccinePetService.findVaccineListByPetId(petId);
        model.addAttribute("pet", pet);
        model.addAttribute("vaccines", vaccinePet);
        return "admin/admin-pet-details";
    }
}


