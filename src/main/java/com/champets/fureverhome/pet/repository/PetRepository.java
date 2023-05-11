package com.champets.fureverhome.pet.repository;

import com.champets.fureverhome.pet.model.dto.PetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.champets.fureverhome.pet.model.Pet;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByActiveTrue();
//    @Query("SELECT p FROM Pet p WHERE p.name = ?1")
//    Optional<Pet> findPetByName(String name);
}
