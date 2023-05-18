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

        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        pets.add(new Pet());
        when(petRepository.findAll()).thenReturn(pets);

        List<PetDto> result = petService.findAllPets();

        assertEquals(2, result.size());
    }

    @Test
    void testFindAllActivePets() {

        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        when(petRepository.findByActiveTrue()).thenReturn(pets);

        List<PetDto> result = petService.findAllActivePets();

        assertEquals(1, result.size());
    }

    @Test
    void testFindActivePetsByFilter() {

        Type type = Type.DOG;
        BodySize size = BodySize.SMALL;
        Gender gender = Gender.MALE;
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet());
        when(petRepository.findPetsByFilter(type, size, gender)).thenReturn(pets);

        List<PetDto> result = petService.findPetsByFilter(type, size, gender);

        assertEquals(1, result.size());
    }

    @Test
    void testFindActivePetsNotAppliedByUser() {

        Long userId = 1L;
        List<Pet> pets = Arrays.asList(new Pet(), new Pet());
        when(petRepository.findPetsNotAppliedByUser(userId)).thenReturn(pets);

        List<PetDto> result = petService.findActivePetsNotAppliedByUser(userId);

        verify(petRepository).findPetsNotAppliedByUser(userId);
        assertEquals(pets.size(), result.size());

        when(petRepository.findPetsNotAppliedByUser(userId)).thenThrow(new RuntimeException("Failed to retrieve pets."));

        assertThrows(RuntimeException.class, () -> petService.findActivePetsNotAppliedByUser(userId));
    }


    @Test
    void testFindActivePetsNotAppliedByUserWithFilter() {

        Long userId = 1L;
        Type type = Type.CAT;
        BodySize size = BodySize.SMALL;
        Gender gender = Gender.FEMALE;
        List<Pet> pets = Arrays.asList(new Pet(), new Pet());
        when(petRepository.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender)).thenReturn(pets);

        List<PetDto> result = petService.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender);

        verify(petRepository).findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender);
        assertEquals(pets.size(), result.size());

        when(petRepository.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender))
                .thenThrow(new RuntimeException("Failed to retrieve pets with filter."));

        assertThrows(RuntimeException.class, () -> petService.findActivePetsNotAppliedByUserWithFilter(userId, type, size, gender));
    }


    @Test
    void testSavePet() {

        Pet pet = new Pet();
        when(petRepository.save(pet)).thenReturn(pet);

        Pet result = petService.savePet(mapToPetDto(pet));

        assertEquals(pet, result);

        when(petRepository.save(any())).thenThrow(new RuntimeException("Failed to save pet."));

        assertThrows(RuntimeException.class, () -> petService.savePet(mapToPetDto(pet)));
    }

    @Test
    public void testSavePet_ExceptionThrown() {

        PetDto petDto = PetDto.builder().build();
        petDto.setId(1L);
        petDto.setName("Max");

        Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenThrow(new RuntimeException());

        Assertions.assertThrows(PetServiceException.class, () -> {
            petService.savePet(petDto);
        });
    }

    @Test
    void testFindPetById() {

        long petId = 1;
        Pet pet = new Pet();
        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetDto result = petService.findPetById(petId);

        assertEquals(mapToPetDto(pet), result);

        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PetNotFoundException.class, () -> petService.findPetById(petId));

        when(petRepository.findById(anyLong())).thenThrow(new RuntimeException("Failed to find pet."));

        assertThrows(RuntimeException.class, () -> petService.findPetById(petId));
    }


    @Test
    void testUpdatePet() {

        Pet pet = new Pet();
        when(petRepository.save(any())).thenReturn(pet);

        petService.updatePet(mapToPetDto(pet));

        verify(petRepository, times(1)).save(any());

        PetDto nullPetDto = null;

        assertThrows(NullPointerException.class, () -> petService.updatePet(nullPetDto));
    }

    @Test
    void testDeletePetVaccinesByPetId() {

        Long petId = 1L;
        Query query = mock(Query.class);
        when(entityManager.createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId")).thenReturn(query);

        petService.deletePetVaccinesByPetId(petId);

        verify(entityManager).createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId");
        verify(query).setParameter("petId", petId);
        verify(query).executeUpdate();

        when(entityManager.createQuery("DELETE FROM VaccinePet v WHERE v.pet.id = :petId"))
                .thenThrow(new RuntimeException("Failed to create query."));

        assertThrows(RuntimeException.class, () -> petService.deletePetVaccinesByPetId(petId));
    }

    @Test
    public void testDeletePetVaccinesByPetId_ExceptionThrown() {

        Long petId = 1L;
        Query query = Mockito.mock(Query.class);
        Mockito.when(entityManager.createQuery(Mockito.anyString())).thenReturn(query);
        Mockito.when(query.executeUpdate()).thenThrow(new RuntimeException());

        Assertions.assertThrows(PetServiceException.class, () -> {
            petService.deletePetVaccinesByPetId(petId);
        });
    }
}
