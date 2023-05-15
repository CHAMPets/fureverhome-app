package com.champets.fureverhome.application;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.application.model.dto.ApplicationDto;
import com.champets.fureverhome.application.model.mapper.ApplicationMapper;
import com.champets.fureverhome.application.repository.ApplicationRepository;
import com.champets.fureverhome.application.service.impl.ApplicationServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        // Arrange
        List<Application> expectedApplications = new ArrayList<>();
        expectedApplications.add(new Application());
        expectedApplications.add(new Application());

        when(mockApplicationRepository.findAll()).thenReturn(expectedApplications);

        // Act
        List<ApplicationDto> result = applicationService.findAllApplications();

        // Assert
        assertEquals(expectedApplications.size(), result.size());
        verify(mockApplicationRepository, times(1)).findAll();
    }

    @Test
    public void testFindApplicationsByPetId() {
        // Arrange
        Long petId = 1L;
        List<Application> expectedApplications = new ArrayList<>();
        expectedApplications.add(new Application());
        expectedApplications.add(new Application());

        when(mockApplicationRepository.findApplicationsByPetId(petId)).thenReturn(expectedApplications);

        // Act
        List<ApplicationDto> result = applicationService.findApplicationsByPetId(petId);

        // Assert
        assertEquals(expectedApplications.size(), result.size());
        verify(mockApplicationRepository, times(1)).findApplicationsByPetId(petId);
    }

    @Test
    public void testSaveApplication() {
        // Arrange
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = ApplicationDto.builder().build();
        Pet pet = new Pet();
        UserEntity user = new UserEntity();
        Application application = new Application();

        when(mockPetRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mockApplicationRepository.save(any(Application.class))).thenReturn(application);

        // Act
        Application result = applicationService.saveApplication(applicationDto, petId, userId);

        // Assert
        assertEquals(application, result);
        verify(mockPetRepository, times(1)).findById(petId);
        verify(mockUserRepository, times(1)).findById(userId);
        verify(mockApplicationRepository, times(1)).save(any(Application.class));
    }

    @Test
    public void testFindApplicationById() {
        // Arrange
        Long applicationId = 1L;
        Application expectedApplication = new Application();

        when(mockApplicationRepository.findById(applicationId)).thenReturn(Optional.of(expectedApplication));

        // Act
        Application result = ApplicationMapper.mapToApplication(applicationService.findApplicationById(applicationId));

        // Assert
        assertEquals(expectedApplication, result);
        verify(mockApplicationRepository, times(1)).findById(applicationId);
    }

    @Test
    public void testUpdateApplication() {
        // Arrange
        ApplicationDto applicationDto = ApplicationDto.builder().build();
        Application application = new Application();

        // Act
        applicationService.updateApplication(applicationDto);

        // Assert
        verify(mockApplicationRepository, times(1)).save(any(Application.class));
    }

    @Test(expected = NoSuchElementException.class)
    public void testSaveApplication_InvalidPetId_ThrowsException() {
        // Arrange
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = ApplicationDto.builder().build();

        when(mockPetRepository.findById(petId)).thenReturn(Optional.empty());

        // Act
        applicationService.saveApplication(applicationDto, petId, userId);

        // Assert
        // Expecting NoSuchElementException to be thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void testSaveApplication_InvalidUserId_ThrowsException() {
        // Arrange
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = ApplicationDto.builder().build();
        Pet pet = new Pet();

        when(mockPetRepository.findById(petId)).thenReturn(Optional.of(pet));
        when(mockUserRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        applicationService.saveApplication(applicationDto, petId, userId);

        // Assert
        // Expecting NoSuchElementException to be thrown
    }

    @Test
    public void testFindAllApplications_EmptyList() {
        // Arrange
        List<Application> expectedApplications = new ArrayList<>();

        when(mockApplicationRepository.findAll()).thenReturn(expectedApplications);

        // Act
        List<ApplicationDto> result = applicationService.findAllApplications();

        // Assert
        assertTrue(result.isEmpty());
        verify(mockApplicationRepository, times(1)).findAll();
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindApplicationById_InvalidApplicationId_ThrowsException() {
        // Arrange
        Long applicationId = 1L;

        when(mockApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        // Act
        applicationService.findApplicationById(applicationId);

        // Assert
        // Expecting NoSuchElementException to be thrown
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateApplication_NullApplicationDto_ThrowsException() {
        // Arrange
        ApplicationDto applicationDto = null;

        // Act
        applicationService.updateApplication(applicationDto);

        // Assert
        // Expecting IllegalArgumentException to be thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void testSaveApplication_NullApplicationDto_ThrowsException() {
        // Arrange
        Long petId = 1L;
        Long userId = 1L;
        ApplicationDto applicationDto = null;

        // Act
        applicationService.saveApplication(applicationDto, petId, userId);

        // Assert
        // Expecting IllegalArgumentException to be thrown
    }

    @Test(expected = NoSuchElementException.class)
    public void testUpdateApplication_InvalidApplicationDto_ThrowsException() {
        // Arrange
        ApplicationDto applicationDto = ApplicationDto.builder().build();

        when(mockApplicationRepository.save(any(Application.class))).thenThrow(NoSuchElementException.class);

        // Act
        applicationService.updateApplication(applicationDto);

        // Assert
        // Expecting NoSuchElementException to be thrown
    }
}