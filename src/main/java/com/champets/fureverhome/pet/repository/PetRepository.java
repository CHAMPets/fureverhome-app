package com.champets.fureverhome.pet.repository;

import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.pet.model.dto.PetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.champets.fureverhome.pet.model.Pet;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByActiveTrue();

    @Query("SELECT p FROM Pet p WHERE (:type is null or p.type = :type) and (:size is null or p.bodySize = :size) and (:gender is null or p.gender = :gender)")
    List<Pet> findByFilter(Type type, BodySize size, Gender gender);

    //    @Query("SELECT p FROM Pet p WHERE p.name = ?1")
//    Optional<Pet> findPetByName(String name);
}
