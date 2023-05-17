package com.champets.fureverhome.vaccine;

import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.repository.VaccineRepository;
import com.champets.fureverhome.vaccine.service.impl.VaccineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        List<Vaccine> vaccines = new ArrayList<>();
        vaccines.add(new Vaccine(1L, "vaccine1", "type1", "desc1"));
        vaccines.add(new Vaccine(2L, "vaccine2", "type2", "desc2"));
        when(vaccineRepositoryMock.findAll()).thenReturn(vaccines);

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
        Vaccine vaccine = new Vaccine(1L, "vaccine1", "type1", "desc1");
        when(vaccineRepositoryMock.findById(1L)).thenReturn(Optional.of(vaccine));

        Vaccine result = vaccineService.findVaccineById(1L);

        assertEquals(vaccine, result);
    }

    @Test
    public void testFindAllVaccinesWhenVaccineListIsEmpty() {
        Mockito.when(vaccineRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<Vaccine> vaccines = vaccineService.findAllVaccines();

        assertEquals(Collections.emptyList(), vaccines);
        Mockito.verify(vaccineRepositoryMock).findAll();
    }

    @Test
    public void testFindVaccineByIDWhenIdDoesNotExist() {
        Long nonExistingVaccineId = 1234L;
        Mockito.when(vaccineRepositoryMock.findById(nonExistingVaccineId)).thenReturn(Optional.empty());

        Vaccine vaccine = vaccineService.findVaccineById(nonExistingVaccineId);

        assertNull(vaccine);
        Mockito.verify(vaccineRepositoryMock).findById(nonExistingVaccineId);
    }
}

