package com.champets.fureverhome.pet.controller;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.service.ApplicationService;
import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.service.UserService;
import com.champets.fureverhome.utility.FileUploadUtil;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.model.dto.VaccinePetDto;
import com.champets.fureverhome.vaccine.service.VaccinePetService;
import com.champets.fureverhome.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.champets.fureverhome.pet.service.PetService;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.champets.fureverhome.pet.model.Pet;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPet;


@Controller
public class PetController {

    private final PetService petService;
    private final VaccineService vaccineService;
    private final VaccinePetService vaccinePetService;
    private final UserService userService;

    private final ApplicationService applicationService;

    @Autowired
    public PetController(PetService petService, VaccineService vaccineService, VaccinePetService vaccinePetService, UserService userService, ApplicationService applicationService) {
        this.petService = petService;
        this.vaccineService = vaccineService;
        this.vaccinePetService = vaccinePetService;
        this.userService = userService;
        this.applicationService = applicationService;
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
    public String listPets(Model model) {
        UserEntity user = userService.getCurrentUser();
        List<PetDto> pets = petService.findAllPets();
        model.addAttribute("user", user);
        model.addAttribute("pets", pets);
        return "admin/admin-home";
    }

    @GetMapping("/home")
    public String listActivePets(Model model) {
        UserEntity user = userService.getCurrentUser();
        List<PetDto> pets = petService.findActivePetsNotAppliedByUser(user.getId());
        model.addAttribute("pets", pets);
        model.addAttribute("user", user);
        return "user/user-home";
    }

    @GetMapping("/home/filtered")
    public String listActivePetsByFilterAsUser(@RequestParam(value = "type") String type,
                                               @RequestParam(value = "size") String size,
                                               @RequestParam(value = "gender") String gender,
                                               Model model) {

        Type enumType = (type != null && !type.equals("ALL") && type != "") ? Type.valueOf(type) : null;
        BodySize enumSize = (size != null && !size.equals("ALL") && size != "") ? BodySize.valueOf(size) : null;
        Gender enumGender = (gender != null && !gender.equals("ALL") && gender != "") ? Gender.valueOf(gender) : null;


        Long userId = userService.getCurrentUser().getId();

        List<PetDto> pets = petService.findActivePetsNotAppliedByUserWithFilter(userId, enumType, enumSize, enumGender);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("pets", pets);
        model.addAttribute("type", type);
        model.addAttribute("size", size);
        model.addAttribute("gender", gender);
        return "user/user-home-filtered";
    }

    @GetMapping("/admin/filtered")
    public String listActivePetsByFilterAsAdmin(@RequestParam(value = "type") String type,
                                                @RequestParam(value = "size") String size,
                                                @RequestParam(value = "gender") String gender,
                                                Model model) {

        Type enumType = (type != null && !type.equals("ALL") && type != "") ? Type.valueOf(type) : null;
        BodySize enumSize = (size != null && !size.equals("ALL") && size != "") ? BodySize.valueOf(size) : null;
        Gender enumGender = (gender != null && !gender.equals("ALL") && gender != "") ? Gender.valueOf(gender) : null;


        UserEntity user = userService.getCurrentUser();
        List<PetDto> pets = petService.findPetsByFilter(enumType, enumSize, enumGender);
        model.addAttribute("user", user);
        model.addAttribute("pets", pets);
        model.addAttribute("type", type);
        model.addAttribute("size", size);
        model.addAttribute("gender", gender);
        return "admin/admin-home-filtered";
    }

    @GetMapping("/admin/pets/new")
    public String createPetForm(Model model) {
        Pet pet = new Pet();
        UserEntity user = userService.getCurrentUser();
        List<Vaccine> vaccines = vaccineService.findAllVaccines();
        model.addAttribute("user", user);
        model.addAttribute("vaccines", vaccines);
        model.addAttribute("pet", pet);
        return "admin/pet-create";
    }

    @PostMapping("/admin/pets/new")
    public String savePet(@Valid @ModelAttribute("pet") PetDto petDto,
                          BindingResult result,
                          @RequestParam(name = "vaccineIds", required = false) List<Long> vaccineIds,
                          Model model,
                          @RequestParam("image") MultipartFile file) throws IOException {

        String nameImage = StringUtils.cleanPath(file.getOriginalFilename());

        if (result.hasErrors() || nameImage == null) {
            List<Vaccine> vaccines = vaccineService.findAllVaccines();
            model.addAttribute("pet", petDto);
            model.addAttribute("vaccines", vaccines);
            return "admin/pet-create";
        }
        petDto.setImagePath("assets/" + nameImage);
        String uploadDir = "target/classes/static/assets/";
        FileUploadUtil.saveFile(uploadDir, nameImage, file);
        List<VaccinePet> vaccineHistory = createVaccineHistory(vaccineIds, petDto);
//        Pet pet = mapToPet(petDto);
        petDto.setVaccineList(vaccineHistory);
        petService.savePet(petDto);
        return "redirect:/admin";
    }

    @GetMapping("/admin/pets/{petId}/edit")
    public String editPetForm(@PathVariable("petId") Long petId, Model model) {
        List<Vaccine> vaccines = vaccineService.findAllVaccines();
        model.addAttribute("vaccine", vaccines);
        PetDto pet = petService.findPetById(petId);
        UserEntity user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("pet", pet);
        return "admin/pet-edit";
    }

    @PostMapping("/admin/pets/{petId}/edit")
    public String updatePet(@PathVariable("petId") Long petId,
                            @Valid @ModelAttribute("pet") PetDto pet,
                            BindingResult result,
                            Model model,
                            @RequestParam(name = "vaccineIds", required = false) List<Long> vaccineIds,
                            @RequestParam("image") MultipartFile file) throws IOException {

        petService.deletePetVaccinesByPetId(petId);
        String nameImage = StringUtils.cleanPath(file.getOriginalFilename());

        if (result.hasErrors() || nameImage == null) {
            List<Vaccine> vaccines = vaccineService.findAllVaccines();
            model.addAttribute("vaccine", vaccines);
            model.addAttribute("pet", pet);
            return "admin/pet-edit";
        }
        pet.setImagePath("assets/" + nameImage);
        String uploadDir = "target/classes/static/assets/";
        FileUploadUtil.saveFile(uploadDir, nameImage, file);
        petService.deletePetVaccinesByPetId(petId);
        List<VaccinePet> vaccineHistory = createVaccineHistory(vaccineIds, pet);
        pet.setVaccineList(vaccineHistory);
        petService.updatePet(pet);
        return "redirect:/admin";
    }

    @GetMapping("/pets/{petId}")
    public String displayPetAsUser(@PathVariable("petId") Long petId, Model model) {
        UserEntity user = userService.getCurrentUser();
        PetDto pet = petService.findPetById(petId);
        List<VaccinePetDto> vaccinePet = vaccinePetService.findVaccineListByPetId(petId);

        Optional<Application> application = applicationService.findApplicationByPetIdAndUserId(petId, user.getId());

        model.addAttribute("pet", pet);
        model.addAttribute("user", user);
        model.addAttribute("vaccines", vaccinePet);
        model.addAttribute("application", application);
        return "user/user-pet-details";
    }

    @GetMapping("/admin/pets/{petId}")
    public String displayPetAsAdmin(@PathVariable("petId") Long petId, Model model) {
        PetDto pet = petService.findPetById(petId);
        UserEntity user = userService.getCurrentUser();
        List<VaccinePetDto> vaccinePet = vaccinePetService.findVaccineListByPetId(petId);
        model.addAttribute("pet", pet);
        model.addAttribute("user", user);
        model.addAttribute("vaccines", vaccinePet);
        return "admin/admin-pet-details";
    }
}



