package com.champets.fureverhome.pet;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.model.dto.PetDto;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.pet.service.PetService;
import com.champets.fureverhome.pet.service.impl.PetServiceImpl;
import com.champets.fureverhome.vaccine.repository.VaccinePetRepository;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPet;
import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPetDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private VaccineRepository vaccineRepository;

    @Mock
    private VaccinePetRepository vaccinePetRepository;

    @Mock
    private EntityManager entityManager;

    private PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        petService = new PetServiceImpl(petRepository, vaccineRepository, vaccinePetRepository, entityManager);
    }

    @Test
    void testFindAllPets() {
        // Arrange
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        pets.add(new Pet());
        when(petRepository.findAll()).thenReturn(pets);

        // Act
        List<PetDto> result = petService.findAllPets();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllActivePets() {
        // Arrange
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        when(petRepository.findByActiveTrue()).thenReturn(pets);

        // Act
        List<PetDto> result = petService.findAllActivePets();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testFindActivePetsByFilter() {
        // Arrange
        Type type = Type.DOG;
        BodySize size = BodySize.SMALL;
        Gender gender = Gender.MALE;
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        when(petRepository.findByFilter(type, size, gender)).thenReturn(pets);

        // Act
        List<PetDto> result = petService.findActivePetsByFilter(type, size, gender);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testSavePet() {
        // Arrange
//        PetDto petDto = new PetDto();
        Pet pet = new Pet();
        when(petRepository.save(pet)).thenReturn(pet);

        // Act
        Pet result = petService.savePet(mapToPetDto(pet));

        // Assert
        assertEquals(pet, result);
    }

    @Test
    void testFindPetById() {
        // Arrange
        long petId = 1;
        Pet pet = new Pet();
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        // Act
        PetDto result = petService.findPetById(petId);

        // Assert
        assertEquals(mapToPetDto(pet), result);
    }

//    @Test
//    void testUpdatePet() {
//        // Arrange
//        Pet pet = new Pet();
//        pet.setVaccineList(new ArrayList<>());
//        when(petRepository.save(pet)).thenReturn(pet);
//
//        // Act
//        petService.updatePet(mapToPetDto(pet));
//
//        // Assert
//        assertEquals(1, pet.getVaccineList().size());
//        verify(petRepository, times(2)).save(pet);
//    }

    @Test
    void testDeletePetVaccinesByPetId() {
        // Arrange
        Long petId = 1L;
        Query query = mock(Query.class);
        when(entityManager.createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId")).thenReturn(query);

        // Act
        petService.deletePetVaccinesByPetId(petId);

        // Assert
        verify(entityManager).createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId");
        verify(query).setParameter("petId", petId);
        verify(query).executeUpdate();
    }
}