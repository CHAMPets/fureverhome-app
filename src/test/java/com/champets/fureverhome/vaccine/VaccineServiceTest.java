package com.champets.fureverhome.vaccine;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import com.champets.fureverhome.vaccine.service.impl.VaccineServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VaccineServiceTest {
    private VaccineRepository vaccineRepositoryMock;
    private VaccineServiceImpl vaccineService;

    @BeforeEach
    void setUp() {
        vaccineRepositoryMock = mock(VaccineRepository.class);
        vaccineService = new VaccineServiceImpl(vaccineRepositoryMock);
    }

    @Test
    void testFindAllVaccines() {
        // Arrange
        List<Vaccine> vaccines = new ArrayList<>();
        vaccines.add(new Vaccine(1L, "vaccine1", "type1", "desc1"));
        vaccines.add(new Vaccine(2L, "vaccine2", "type2", "desc2"));
        when(vaccineRepositoryMock.findAll()).thenReturn(vaccines);

        // Act
        List<Vaccine> result = vaccineService.findAllVaccines();

        // Assert
        assertEquals(2, result.size());
        assertEquals("vaccine1", result.get(0).getName());
        assertEquals("type1", result.get(0).getType());
        assertEquals("desc1", result.get(0).getDescription());
        assertEquals("vaccine2", result.get(1).getName());
        assertEquals("type2", result.get(1).getType());
        assertEquals("desc2", result.get(1).getDescription());
    }

    @Test
    void testFindVaccineById() {
        // Arrange
        Vaccine vaccine = new Vaccine(1L, "vaccine1", "type1", "desc1");
        when(vaccineRepositoryMock.findById(1L)).thenReturn(Optional.of(vaccine));

        // Act
        Vaccine result = vaccineService.findVaccineById(1L);

        // Assert
        assertEquals(vaccine, result);
    }

    @Test
    public void testFindAllVaccinesWhenVaccineListIsEmpty() {
        // Arrange
        Mockito.when(vaccineRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Vaccine> vaccines = vaccineService.findAllVaccines();

        // Assert
        assertEquals(Collections.emptyList(), vaccines);
        Mockito.verify(vaccineRepositoryMock).findAll();
    }

    @Test
    public void testFindVaccineByIDWhenIdDoesNotExist() {
        // Arrange
        Long nonExistingVaccineId = 1234L;
        Mockito.when(vaccineRepositoryMock.findById(nonExistingVaccineId)).thenReturn(Optional.empty());

        // Act
        Vaccine vaccine = vaccineService.findVaccineById(nonExistingVaccineId);

        // Assert
        assertNull(vaccine);
        Mockito.verify(vaccineRepositoryMock).findById(nonExistingVaccineId);
    }
}

