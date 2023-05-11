package com.champets.fureverhome.pet.controller;

import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.champets.fureverhome.pet.service.PetService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.champets.fureverhome.pet.model.Pet;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PetController {
    private final PetService petService;
    private final VaccineService vaccineService;

    @Autowired
    public PetController(PetService petService, VaccineService vaccineService) {

        this.petService = petService;
        this.vaccineService = vaccineService;
    }

    @GetMapping("/pets")
    public String listPets(Model model){
        List<PetDto> pets = petService.findAllPets();
        model.addAttribute("pets", pets);
        return "admin/admin-home";
    }

    @GetMapping("/pets/home")
    public String listActivePets(Model model){
        List<PetDto> pets = petService.findAllActivePets();
        model.addAttribute("pets", pets);
        return "user/user-home";
    }

    @GetMapping("pets/new")
    public String createPetForm(Model model){
        Pet pet = new Pet();
        List<Vaccine> vaccines = vaccineService.findAllVaccines();
        model.addAttribute("vaccines", vaccines);
        model.addAttribute("pet", pet);
        return "admin/pet-create";
    }
    @PostMapping("pets/new")
    public String savePet(@ModelAttribute("pet") Pet pet){

        petService.savePet(pet);
        return "redirect:/pets";
    }

    @GetMapping("/pets/{petId}/edit")
    public String editPetForm(@PathVariable("petId") Long petId, Model model){
        PetDto pet = petService.findPetById(petId);
        model.addAttribute("pet", pet);
        return "admin/pet-edit";
    }

    @PostMapping("pets/{petId}/edit")
    public String updatePet(@PathVariable("petId") Long petId,
                            @Valid @ModelAttribute("pet") PetDto pet,
                            BindingResult result, Model model){
        if(result.hasErrors()) {
            model.addAttribute("pet", pet);
            return "admin/pet-edit";
        }
        pet.setId(petId);
        petService.updatePet(pet);
        return "redirect:/pets";
    }

    @GetMapping("pets/{petId}")
    public String displayPet(@PathVariable("petId") Long petId, Model model){
        PetDto pet = petService.findPetById(petId);
        model.addAttribute("pet", pet);
        return "user/pet-details";
    }
}
