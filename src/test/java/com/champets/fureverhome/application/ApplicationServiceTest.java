package com.champets.fureverhome.application;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.model.mapper.ApplicationMapper;
import com.champets.fureverhome.application.repository.ApplicationRepository;
import com.champets.fureverhome.application.service.impl.ApplicationServiceImpl;
import com.champets.fureverhome.exception.application.ApplicationNotFoundException;
import com.champets.fureverhome.exception.pet.PetNotFoundException;
import com.champets.fureverhome.exception.user.UserNotFoundException;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.pet.repository.PetRepository;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository mockApplicationRepository;

    @Mock
    private PetRepository mockPetRepository;

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllApplications() {
        List<Application> expectedApplications = Arrays.asList(new Application(), new Application());

        when(mockApplicationRepository.findAll()).thenReturn(expectedApplications);

        List<ApplicationDto> result = applicationService.findAllApplications();

        assertEquals(expectedApplications.size(), result.size());
        verify(mockApplicationRepository, times(1)).findAll();
    }

    @Test
    public void testFindApplicationsByPetId() {
        Long petId = 1L;
        List<Application> expectedApplications = Arrays.asList(new Application(), new Application());

        when(mockApplicationRepository.findApplicationsByPetId(petId)).thenReturn(expectedApplications);

        List<ApplicationDto> result = applicationService.findApplicationsByPetId(petId);

        assertEquals(expectedApplications.size(), result.size());
        verify(mockApplicationRepository, times(1)).findApplicationsByPetId(petId);
    }

    @Test
    public void testFindApplicationsByUserId() {
        Long userId = 1L;
        List<Application> expectedApplications = Arrays.asList(new Application(), new Application());

        when(mockApplicationRepository.findApplicationsByUserId(userId)).thenReturn(expectedApplications);

        List<ApplicationDto> result = applicationService.findApplicationsByUserId(userId);

        assertEquals(expectedApplications.size(), result.size());
        verify(mockApplicationRepository, times(1)).findApplicationsByUserId(userId);
    }

    @Test
    public void testFindApplicationsByPetIdAndUserId() {
        Long petId = 1L;
        Long userId = 1L;
        Optional<Application> expectedApplication = Optional.of(new Application());

        when(mockApplicationRepository.findApplicationByPetIdAndUserId(petId, userId)).thenReturn(expectedApplication);

        Optional<Application> result = applicationService.findApplicationByPetIdAndUserId(petId, userId);

        assertNotNull(result);
        assertEquals(expectedApplication, result);
        verify(mockApplicationRepository, times(1)).findApplicationByPetIdAndUserId(petId, userId);
    }

    @Test
    public void testSaveApplication() {
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = ApplicationDto.builder().build();
        Pet pet = new Pet();
        UserEntity user = new UserEntity();
        Application application = new Application();

        when(mockPetRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mockApplicationRepository.save(any(Application.class))).thenReturn(application);

        Application result = applicationService.saveApplication(applicationDto, petId, userId);

        assertEquals(application, result);
        verify(mockPetRepository, times(1)).findById(petId);
        verify(mockUserRepository, times(1)).findById(userId);
        verify(mockApplicationRepository, times(1)).save(any(Application.class));
    }

    @Test
    public void testFindApplicationById() {
        Long applicationId = 1L;
        Application expectedApplication = new Application();

        when(mockApplicationRepository.findById(applicationId)).thenReturn(Optional.of(expectedApplication));

        Application result = ApplicationMapper.mapToApplication(applicationService.findApplicationById(applicationId));

        assertEquals(expectedApplication, result);
        verify(mockApplicationRepository, times(1)).findById(applicationId);
    }

    @Test
    public void testUpdateApplication() {
        ApplicationDto applicationDto = ApplicationDto.builder().build();

        applicationService.updateApplication(applicationDto);

        verify(mockApplicationRepository, times(1)).save(any(Application.class));
    }

    @Test
    public void testFindApplicationByPetIdAndUserId_InvalidPetId_ReturnsNull() {
        Long petId = 1L;
        Long userId = 1L;

        when(mockApplicationRepository.findApplicationByPetIdAndUserId(petId, userId)).thenReturn(null);

        Optional<Application> result = applicationService.findApplicationByPetIdAndUserId(petId, userId);

        assertNull(result);
    }

    @Test
    public void testFindApplicationByPetIdAndUserId_InvalidUserId_ReturnsNull() {
        Long petId = 1L;
        Long userId = 1L;

        when(mockApplicationRepository.findApplicationByPetIdAndUserId(petId, userId)).thenReturn(null);

        Optional<Application> result = applicationService.findApplicationByPetIdAndUserId(petId, userId);

        assertNull(result);
    }

    @Test(expected = UserNotFoundException.class)
    public void testSaveApplication_InvalidUserId_ThrowsException() {
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = ApplicationDto.builder().build();
        Pet pet = new Pet();

        when(mockPetRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(mockUserRepository.findById(userId)).thenReturn(Optional.empty());

        applicationService.saveApplication(applicationDto, petId, userId);
    }

    @Test
    public void testFindAllApplications_EmptyList() {
        List<Application> expectedApplications = Collections.emptyList();

        when(mockApplicationRepository.findAll()).thenReturn(expectedApplications);

        List<ApplicationDto> result = applicationService.findAllApplications();

        assertTrue(result.isEmpty());
        verify(mockApplicationRepository, times(1)).findAll();
    }

    @Test(expected = ApplicationNotFoundException.class)
    public void testFindApplicationById_InvalidApplicationId_ThrowsException() {
        Long applicationId = 1L;

        when(mockApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        applicationService.findApplicationById(applicationId);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateApplication_NullApplicationDto_ThrowsException() {
        ApplicationDto applicationDto = null;

        applicationService.updateApplication(applicationDto);
    }

    @Test(expected = PetNotFoundException.class)
    public void testSaveApplication_NullApplicationDto_ThrowsException() {
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = null;

        applicationService.saveApplication(applicationDto, petId, userId);
    }

    @Test(expected = NoSuchElementException.class)
    public void testUpdateApplication_InvalidApplicationDto_ThrowsException() {
        ApplicationDto applicationDto = ApplicationDto.builder().build();

        when(mockApplicationRepository.save(any(Application.class))).thenThrow(NoSuchElementException.class);

        applicationService.updateApplication(applicationDto);
    }
}
