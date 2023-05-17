package com.champets.fureverhome.application.repository;

import com.champets.fureverhome.application.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query("SELECT a from Application a WHERE a.pet.id = :petId")
    List<Application> findApplicationsByPetId(Long petId);

    @Query("SELECT a from Application a WHERE a.user.id = :userId")
    List<Application> findApplicationsByUserId(Long userId);

    @Query("SELECT a from Application a WHERE a.pet.id = :petId AND a.user.id = :userId")
    Optional<Application> findApplicationByPetIdAndUserId(Long petId, Long userId);
}
