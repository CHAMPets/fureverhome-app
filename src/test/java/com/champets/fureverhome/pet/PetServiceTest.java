package com.champets.fureverhome.pet;

import com.champets.fureverhome.exception.pet.PetNotFoundException;
import com.champets.fureverhome.exception.pet.PetServiceException;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPet;
import static com.champets.fureverhome.pet.model.mapper.PetMapper.mapToPetDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(petRepository.findPetsByFilter(type, size, gender)).thenReturn(pets);

        // Act
        List<PetDto> result = petService.findPetsByFilter(type, size, gender);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testFindActivePetsNotAppliedByUser() {
        // Arrange
        Long userId = 1L;
        List<Pet> pets = Arrays.asList(new Pet(), new Pet());
        when(petRepository.findPetsNotAppliedByUser(userId)).thenReturn(pets);

        // Act
        List<PetDto> result = petService.findActivePetsNotAppliedByUser(userId);

        // Assert
        verify(petRepository).findPetsNotAppliedByUser(userId);
        assertEquals(pets.size(), result.size());

        // Arrange (Negative Test)
        when(petRepository.findPetsNotAppliedByUser(userId)).thenThrow(new RuntimeException("Failed to retrieve pets."));

        // Act and Assert (Negative Test)
        assertThrows(RuntimeException.class, () -> petService.findActivePetsNotAppliedByUser(userId));
    }


    @Test
    void testFindActivePetsNotAppliedByUserWithFilter() {
        // Arrange
        Long userId = 1L;
        Type type = Type.CAT;
        BodySize size = BodySize.SMALL;
        Gender gender = Gender.FEMALE;
        List<Pet> pets = Arrays.asList(new Pet(), new Pet());
        when(petRepository.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender)).thenReturn(pets);

        // Act
        List<PetDto> result = petService.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender);

        // Assert
        verify(petRepository).findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender);
        assertEquals(pets.size(), result.size());

        // Arrange (Negative Test)
        when(petRepository.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender))
                .thenThrow(new RuntimeException("Failed to retrieve pets with filter."));

        // Act and Assert (Negative Test)
        assertThrows(RuntimeException.class, () -> petService.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender));
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

        // Arrange (Negative Test)
        when(petRepository.save(any())).thenThrow(new RuntimeException("Failed to save pet."));

        // Act and Assert (Negative Test)
        assertThrows(RuntimeException.class, () -> petService.savePet(mapToPetDto(pet)));
    }

    @Test
    public void testSavePet_ExceptionThrown() {
        // Arrange
        PetDto petDto = PetDto.builder().build();
        petDto.setId(1L);
        petDto.setName("Max");

        Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenThrow(new RuntimeException());

        // Act and Assert
        Assertions.assertThrows(PetServiceException.class, () -> {
            petService.savePet(petDto);
        });
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

        // Arrange (Negative Test: Empty Optional)
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert (Negative Test: Empty Optional)
        assertThrows(PetNotFoundException.class, () -> petService.findPetById(petId));

        // Arrange (Negative Test: Exception)
        when(petRepository.findById(anyLong())).thenThrow(new RuntimeException("Failed to find pet."));

        // Act and Assert (Negative Test: Exception)
        assertThrows(RuntimeException.class, () -> petService.findPetById(petId));
    }


    @Test
    void testUpdatePet() {

        Pet pet = new Pet();
        when(petRepository.save(any())).thenReturn(pet);

        // Act
        petService.updatePet(mapToPetDto(pet));

        // Assert
        verify(petRepository, times(1)).save(any());

        // Arrange (Negative Test)
        PetDto nullPetDto = null;

        // Act and Assert (Negative Test)
        assertThrows(NullPointerException.class, () -> petService.updatePet(nullPetDto));
    }

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

        // Arrange (Negative Test)
        when(entityManager.createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId"))
                .thenThrow(new RuntimeException("Failed to create query."));

        // Act and Assert (Negative Test)
        assertThrows(RuntimeException.class, () -> petService.deletePetVaccinesByPetId(petId));
    }

    @Test
    public void testDeletePetVaccinesByPetId_ExceptionThrown() {
        // Arrange
        Long petId = 1L;
        Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.executeUpdate()).thenThrow(new RuntimeException());

        // Act and Assert
        Assertions.assertThrows(PetServiceException.class, () -> {
            petService.deletePetVaccinesByPetId(petId);
        });
    }
}
