package com.champets.fureverhome.vaccine;

import com.champets.fureverhome.exception.pet.PetNotFoundException;
import com.champets.fureverhome.exception.vaccine.VaccineNotFoundException;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import com.champets.fureverhome.vaccine.model.dto.VaccinePetDto;
import com.champets.fureverhome.vaccine.repository.VaccinePetRepository;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import com.champets.fureverhome.vaccine.service.impl.VaccinePetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VaccinePetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private VaccineRepository vaccineRepository;

    @Mock
    private VaccinePetRepository vaccinePetRepository;

    @InjectMocks
    private VaccinePetServiceImpl vaccinePetService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveVaccinePet() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");

        Vaccine vaccine = new Vaccine();
        vaccine.setId(2L);
        vaccine.setName("Rabies");

        VaccinePetDto vaccinePetDto = VaccinePetDto.builder()
                .id(3L)
                .vaccine(vaccine)
                .pet(pet)
                .build();

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(vaccineRepository.findById(2L)).thenReturn(Optional.of(vaccine));

        VaccinePet savedVaccinePet = vaccinePetService.saveVaccinePet(vaccinePetDto, 1L, 2L);

        assertEquals(vaccine, savedVaccinePet.getVaccine());
        assertEquals(pet, savedVaccinePet.getPet());
    }

    @Test
    public void testSaveVaccinePet_withNonExistentVaccineId() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Fluffy");

        VaccinePetDto vaccinePetDto = VaccinePetDto.builder()
                .id(3L)
                .vaccine(Vaccine.builder().id(2L).name("Rabies").build())
                .pet(pet)
                .build();

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(vaccineRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(VaccineNotFoundException.class, () -> {
            vaccinePetService.saveVaccinePet(vaccinePetDto, 1L, 2L);
        });
    }

    @Test
    public void testFindVaccineListByPetId() {
        Pet pet = Pet.builder().id(1L).name("Fluffy").age(3).build();

        Vaccine vaccine = Vaccine.builder().id(1L).name("Rabies").build();

        List<VaccinePet> vaccinePetList = new ArrayList<>();
        vaccinePetList.add(VaccinePet.builder().id(1L).pet(pet).vaccine(vaccine).build());

        when(vaccinePetRepository.findVaccinesByPetId(1L)).thenReturn(vaccinePetList);

        List<VaccinePetDto> vaccineList = vaccinePetService.findVaccineListByPetId(1L);

        verify(vaccinePetRepository).findVaccinesByPetId(1L);

        assertEquals(1, vaccineList.size());
        assertEquals(vaccine.getId(), vaccineList.get(0).getVaccine().getId());
        assertEquals(pet.getId(), vaccineList.get(0).getPet().getId());
    }

    @Test
    public void testFindVaccineListByPetId_withNonExistentPetId() {
        when(vaccinePetRepository.findVaccinesByPetId(2L)).thenReturn(new ArrayList<>());

        List<VaccinePetDto> vaccineList = vaccinePetService.findVaccineListByPetId(2L);

        verify(vaccinePetRepository).findVaccinesByPetId(2L);

        assertTrue(vaccineList.isEmpty());
    }


    @Test
    public void testFindPetsWithVaccine() {
        VaccinePet vaccinePet1 = new VaccinePet();
        vaccinePet1.setId(1L);

        VaccinePet vaccinePet2 = new VaccinePet();
        vaccinePet2.setId(2L);

        Vaccine vaccine = new Vaccine();
        vaccine.setId(1L);
        vaccine.setName("5-in-1 Vaccine");

        vaccinePet1.setVaccine(vaccine);
        vaccinePet2.setVaccine(vaccine);

        List<VaccinePet> vaccinePetList = Arrays.asList(vaccinePet1, vaccinePet2);

        when(vaccinePetRepository.findPetsWithVaccineId(1L)).thenReturn(vaccinePetList);

        List<VaccinePetDto> result = vaccinePetService.findPetsWithVaccine(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    public void testFindPetsWithVaccine_withNonExistentVaccineId() {
        when(vaccinePetRepository.findPetsWithVaccineId(1L)).thenReturn(Collections.emptyList());

        List<VaccinePetDto> result = vaccinePetService.findPetsWithVaccine(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateVaccinePet() {
        VaccinePetDto vaccinePetDto = VaccinePetDto.builder()
                .id(1L)
                .vaccine(Vaccine.builder().id(2L).name("Vaccine B").build())
                .pet(Pet.builder().id(3L).name("Pet C").build())
                .build();

        VaccinePet existingVaccinePet = VaccinePet.builder()
                .id(1L)
                .vaccine(Vaccine.builder().id(1L).name("Vaccine A").build())
                .pet(Pet.builder().id(2L).name("Pet B").build())
                .build();
        Mockito.when(vaccinePetRepository.findById(1L)).thenReturn(Optional.of(existingVaccinePet));

        vaccinePetService.updateVaccinePet(vaccinePetDto);

        verify(vaccinePetRepository, Mockito.times(1)).save(VaccinePet.builder()
                .id(1L)
                .vaccine(Vaccine.builder().id(2L).name("Vaccine B").build())
                .pet(Pet.builder().id(3L).name("Pet C").build())
                .build());
    }

    @Test
    void testUpdateVaccinePet_withNonexistentId_shouldThrowException() {
        VaccinePetDto vaccinePetDto = VaccinePetDto.builder()
                .id(1L)
                .vaccine(Vaccine.builder().id(2L).name("Vaccine B").build())
                .pet(Pet.builder().id(3L).name("Pet C").build())
                .build();

        Mockito.when(vaccinePetRepository.findById(1L)).thenReturn(Optional.empty());

        verify(vaccinePetRepository, never()).save(any());
    }
}
